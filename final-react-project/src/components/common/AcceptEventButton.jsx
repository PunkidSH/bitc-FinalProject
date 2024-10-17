import {useParams} from "react-router-dom";
import {useState} from "react";
import axios from "axios";


function AcceptEventButton() {
  const [eventData, setEventData] = useState([]);
  const { eventId } = useParams();
  const { userId } = useParams();

  // 행사 승인 버튼 (승인대기 / 승인완료)
  const handleAcceptEvent = async(userId) => {
    const confirmed = window.confirm('행사 승인하시겠습니까?');

    if (confirmed) {
      await axios.put(`http://localhost:8080/event/acceptEvent/${eventId}`);
      setEventData(eventData.filter(eventData => eventData.eventId !== eventId));
      alert("승인되었습니다.");
    } else {
      // console.error("승인 중 오류 발생:", error);
    }
  };


  return (
    <button type={'button'} className={'btn btn-outline-secondary me-2'} onClick={() => handleAcceptEvent(eventData.userId)}>
      {eventData.eventAccept === 1 && <p className={'redMark'}>승인대기</p> ||
        eventData.eventAccept === 2 && <p className={'blueMark'}>승인완료</p> ||
        eventData.eventAccept === 3 && <p className={'redMark'}>승인대기</p> ||
        eventData.eventAccept === 'null' && <p className={'grayMark'}>null</p>
      }
    </button>
  )
}

export default AcceptEventButton;