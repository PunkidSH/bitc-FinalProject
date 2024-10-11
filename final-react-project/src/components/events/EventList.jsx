import Events from "../../pages/Events.jsx";
import {Link, NavLink} from "react-router-dom";
import {useEffect, useState} from "react";
import axios from "axios";

function EventList () {
  const [eventData, setEventData] = useState([
    {eventId: 1, image:'/noimg.png', eventTitle: 'AI 행사1', eventDate:'2024년 10월 15일', startTime:'오후 2시', endTime:'오후 5시',
      visibleDate:'2024년 10월 1일', approval:'승인예정', deadline:'진행 중'},
    {eventId: 2, image:'/noimg.png', eventTitle: 'AI 행사1', eventDate:'2024년 10월 15일', startTime:'오후 2시', endTime:'오후 5시',
      visibleDate:'2024년 10월 1일', approval:'승인완료', deadline:'마감'},

  ]);

  // 리스트 데이터 불러옴
  // const [eventData, setEventData] = useState();
  //
  // useEffect(() => {
  //   axios.get('http://localhost:8080/event')
  //     .then(res => {
  //       setEventData(res.data);
  //       //console.log(eventData);
  //     })
  //     .catch(err => {
  //       alert("통신 실패." + err);
  //     });
  // }, [eventData]);

  // 상세페이지에서 승인한 결과 DB 정보에 따라 색상 다르게 출력
  const approvalBtnStyle = (item) => {
    switch (item) {
      case '승인예정':
        return {
          color: '#dc3545',
          border: '1px solid #dc3545',
        };
      case '승인완료':
        return {
          color: '#283eae',
          border: '1px solid #283eae',
        };
      default:
        return {};
    }
  };


  return (
    <section>
      <Events/>
        <div className={'d-flex justify-content-end mb-5'}>
          <Link to={'/events/write'} type={'submit'} className={'btn btn-danger'}>행사 등록</Link>
        </div>

      {eventData?.map(item => (
        <div key={item.eventId} className={'d-flex justify-content-between align-items-center pb-5'}>
          <div className={'col-3'}><Link to={`/events/${item.eventId}`}> <img src={item.image} alt={item.eventTitle} className={'w-100'}/></Link></div>
          <div className={'col-9 ps-5 d-flex align-items-center'}>

            <div className={'col-10'}>
              <p className={'mb-2 approval-btn'} style={approvalBtnStyle(item.approval)}>{item.approval}</p> {/* 승인예정 / 승인완료 */}
              <Link to={`/events/${item.eventId}`}><h4>{item.eventTitle}</h4></Link>
              <ul className={'ps-0 mt-3'}>
                <li>행사기간 : <span>{item.eventDate}</span></li>
                <li className={'my-1'}>행사시간 : <span>{item.startTime} ~ {item.endTime}</span></li>
                <li>게시일 : <span>{item.visibleDate}</span></li>
              </ul>
            </div>

            <div className={'col-2'}>
              <NavLink to={'/events/attend'} className={'btn w-100 btn-point'}>참석현황</NavLink>
            </div>
          </div>
        </div>
        ))}
    </section>
  )
}

export default EventList;