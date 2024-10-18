import {useNavigate} from "react-router-dom";

function SecretaryButton() {
  const navigate = useNavigate();

  return (
 // 글 작성자 view
  <div className={'d-flex justify-content-between mt-5'}>
   <div className={'justify-content-start'}>
      <button type={'button'} className={'btn btn-point'} onClick={() => navigate('/')}>목록</button>
  </div>
   <div className={'justify-content-end'}>
     <button type={'button'} className={'btn btn-outline-danger me-2'}>삭제</button>
     <button type={'button'} className={'btn btn-outline-point me-2'}>수정</button>
     <button type={'button'} className={'btn btn-outline-point'}>마감</button>
    </div>
  </div>
  )
}

export default SecretaryButton;