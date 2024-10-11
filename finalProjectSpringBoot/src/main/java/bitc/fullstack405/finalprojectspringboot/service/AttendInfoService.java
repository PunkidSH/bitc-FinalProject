package bitc.fullstack405.finalprojectspringboot.service;

import bitc.fullstack405.finalprojectspringboot.database.dto.app.AppCertificateResponse;
import bitc.fullstack405.finalprojectspringboot.database.entity.*;
import bitc.fullstack405.finalprojectspringboot.database.repository.AttendInfoRepository;
import bitc.fullstack405.finalprojectspringboot.database.repository.EventAppRepository;
import bitc.fullstack405.finalprojectspringboot.database.repository.EventScheduleRepository;
import bitc.fullstack405.finalprojectspringboot.database.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class AttendInfoService {

    private final AttendInfoRepository attendInfoRepository;
    private final EventScheduleRepository eventScheduleRepository;
    private final EventAppRepository eventAppRepository;
    private final UserRepository userRepository;

    // <APP> QR 이미지 조회
    // eventId와 userId로 scheduleId, eventDate, qrImage 조회
    public List<Map<String, Object>> findQrImages(Long eventId, Long userId) {
        List<Object[]> results = eventScheduleRepository.findQrImagesByEventIdAndUserId(eventId, userId);

        return results.stream()
                .map(result -> {
                    Map<String, Object> map = new HashMap<>();
                    map.put("scheduleId", Long.valueOf(result[0].toString())); // scheduleId는 Long 타입으로 변환
                    map.put("eventDate", result[1].toString()); // eventDate는 LocalDate이므로 toString()으로 변환
                    map.put("qrImage", result[2].toString());
                    return map;
                })
                .collect(Collectors.toList());
    }

    // <APP> QR 스캔 - 올바른 행사 정보인지 확인
    public boolean validEvent(Long scheduleId, Long userId) {
        return attendInfoRepository.isEventValid(scheduleId, userId);
    }

    // <APP> QR 스캔 - 이미 입장/퇴장 체크를 다 한 QR 코드인지 확인
    public boolean usedQR(Long scheduleId, Long userId) {
        return attendInfoRepository.existsByEventSchedule_ScheduleIdAndUser_UserIdAndCheckInTimeIsNotNullAndCheckOutTimeIsNotNull(scheduleId, userId);
    }

    // <APP> QR 스캔 - 입장/퇴장 데이터 업데이트, 회차별 수료/미수료 업데이트, 행사 수료/미수료 업데이트
    @Transactional
    public void qrScan(Long eventId, Long scheduleId, Long userId) {

        // attendInfoEntity 가져오기
        AttendInfoEntity attendInfo = attendInfoRepository.findByEventSchedule_ScheduleIdAndUser_UserId(scheduleId, userId)
                .orElseThrow(() -> new IllegalArgumentException("not found attendInfo"));

        // eventScheduleEntity 가져오기
        EventScheduleEntity eventSchedule = eventScheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new IllegalArgumentException("not found eventSchedule"));

        // 입장/퇴장 처리
        if(attendInfoRepository.existsByEventSchedule_ScheduleIdAndUser_UserIdAndCheckInTimeIsNull(scheduleId, userId)) { // 입장
            attendInfo.updateCheckIn(LocalDate.now(), LocalTime.now());
        }
        else { // 퇴장
            if (attendInfo.getCheckInTime().isAfter(eventSchedule.getStartTime()) ||
                    LocalTime.now().isBefore(eventSchedule.getEndTime())) {
                // 지각 or 일찍 퇴장 시 해당 회차 미수료 => 퇴장 시간만 업데이트
                attendInfo.updateCheckOut(LocalTime.now());
            } else {
                // 해당 회차 수료 => 퇴장 시간 & 해당 회차 수료 N->Y 업데이트
                attendInfo.updateCheckOutComp(LocalTime.now());
            }

            // 회차의 마지막 퇴장 시 행사 수료 여부 업데이트
            // 행사에 속한 모든 회차(스케줄) 중 마지막 회차인지 확인
            if (isLastSchedule(eventId, scheduleId)) {
                // 최종 행사 수료 여부 업데이트
                updateEventCompletionStatus(eventId, userId);
            }
        }
    }

    // <APP> 해당 회차가 해당 행사(eventId)의 마지막 회차인지 확인
    private boolean isLastSchedule(Long eventId, Long scheduleId) {
        // eventId로 해당 행사에 속한 모든 schedule 가져오기(scheduleId 기준 내림차순)
        List<EventScheduleEntity> eventSchedules = eventScheduleRepository.findByEvent_EventIdOrderByScheduleIdDesc(eventId);

        // schedule 리스트 중에서 마지막 회차의 scheduleId가 현재 처리 중인 scheduleId와 동일한지 확인
        return !eventSchedules.isEmpty() && eventSchedules.get(0).getScheduleId().equals(scheduleId);
    }

    // <APP> 해당 행사(eventId)의 수료 여부 업데이트
    private void updateEventCompletionStatus(Long eventId, Long userId) {
        // 모든 회차의 수료 여부 확인 => 행사 수료 여부 업데이트
        // 예를 들어, 모든 회차가 수료되었을 때만 행사 수료 상태를 Y로 업데이트
        boolean isAllCompleted =  attendInfoRepository.isAllCompleted(eventId, userId);

        // 회차별 수료가 모두 Y => 행사 수료 N->Y 업데이트
        if (isAllCompleted) {
            EventAppEntity eventApp = eventAppRepository.findByEvent_EventIdAndUser_UserId(eventId, userId);
            eventApp.updateEventComp();
        }
    }

    // <APP> 교육 참석증
//    private AppCertificateResponse certificate (Long eventId, Long userId) {
//        // 협회장 이름 조회
//        String presidentName = userRepository.findByRold
//    }
}