import {useParams} from "react-router-dom";
import {useEffect, useState} from "react";
import axios from "axios";

function DenyEventButton() {
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

  // 행사 승인거부 버튼
  const handleDenyEvent = async() => {
    const confirmed = window.confirm('행사 승인 거부하시겠습니까?');

    if (confirmed) {
      await axios.put(`http://localhost:8080/event/denyEvent/${eventId}`);
      setEventData(eventData.filter(eventData => eventData.eventId !== eventId));
      setIsButtonDisabled(true);
      localStorage.setItem('isButtonDisabled', 'true'); // 상태를 로컬 스토리지에 저장
      alert("승인 거부되었습니다.");
    } else {
      // console.error("승인거부 중 오류 발생:", error);
    }
  };


  return (
    <button type={'button'} className={'btn btn-outline-danger me-2'}
            onClick={() => handleDenyEvent(eventData.eventId)} disabled={isButtonDisabled}>
      {isButtonDisabled ? '승인거부' : '승인거부'}
    </button>
  )
}

export default DenyEventButton;