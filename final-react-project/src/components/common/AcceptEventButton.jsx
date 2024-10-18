import {useParams} from "react-router-dom";
import {useEffect, useState} from "react";
import axios from "axios";



function AcceptEventButton() {
  const [eventData, setEventData] = useState([]);
  const {eventId} = useParams();
  const userId = sessionStorage.getItem("userId");


  // 행사 승인 버튼 (승인대기 / 승인완료)
  const handleAcceptEvent = async(userId) => {
    const confirmed = window.confirm('행사 승인하시겠습니까?');

    if (confirmed) {
      await axios.put(`http://localhost:8080/event/acceptEvent/${eventId}`, {
        params: {
          userId: userId
        }
      })
      setEventData(eventData.filter(eventData => eventData.eventId !== eventId));
      // setEventData(eventData.eventId);
      alert("승인되었습니다.");
    } else {
      // console.error("승인 중 오류 발생:", error);
    }
  };

  return (
    <button type={'button'} className={'btn btn-outline-secondary me-2'}
            onClick={() => handleAcceptEvent(eventData.eventId)}>
      {eventData.eventAccept === 1 ? (
        <div>승인대기</div>
      ) : eventData.eventAccept === 2 ? (
        <div>승인완료</div>
      ) : eventData.eventAccept === 3 ? (
        <div>승인대기</div>
      )  : (
        <div>null</div>
      )
      }
    </button>
  )
}
export default AcceptEventButton;