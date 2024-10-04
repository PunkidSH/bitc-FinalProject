import axios from "axios";
import {useEffect, useState} from "react";
import TempItems from "./TempItems.jsx";

function TempComponent() {

  const [tempData, setTempData] = useState();

  useEffect(() => {
    axios.get('http://localhost:8080/temp')
      .then(res => {
        setTempData(res.data);
        // console.log(tempData); // 콘솔로그사용시 무한렌더링 요청 발생
      })
      .catch(err => {
        alert("통신 실패." + err);
      });
  },[]);

  return (
    <div className={'container mt-5'}>
      <h1>TempComponent</h1>

      {tempData?.map(item => {
        return <TempItems key={item.id} data={item} />
      })}
    </div>
  );
}

export default TempComponent;