
import axios from "axios";
import {useEffect, useState} from "react";
import {useParams} from "react-router-dom";

function EndEventButton() {
  const [eventData, setEventData] = useState([]);
  const { eventId } = useParams();
  const [isButtonDisabled, setIsButtonDisabled] = useState(false); // 버튼 비활성화

  // 새로고침 => 버튼 비활성화 고정
  // useEffect(() => {
  //   const savedState = localStorage.getItem('isButtonDisabled');
  //   if (savedState === 'true') {
  //     setIsButtonDisabled(true);
  //   }
  // }, []);


  // 모집종료 버튼
  const handleEndEvent = async() => {
    const confirmed = window.confirm('행사 모집 종료하시겠습니까?');

    if (confirmed) {
      await axios.put(`http://localhost:8080/event/endEvent/${eventId}`);
      setEventData(eventData.filter(eventData => eventData.eventId !== eventId));
      setIsButtonDisabled(true);
      localStorage.setItem('isButtonDisabled', 'true'); // 상태를 로컬 스토리지에 저장
      alert("행사 모집종료되었습니다.");
    } else {
      // console.error("모집종료 중 오류 발생:", error);
    }
  };

  return (
    <button type={'button'} className={'btn btn-danger'}
            onClick={() => handleEndEvent(eventData.eventId)} disabled={isButtonDisabled}>
      {isButtonDisabled ? '모집종료' : '모집종료'}
    </button>
  )
}

export default EndEventButton;