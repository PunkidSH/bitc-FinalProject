function SignUp() {
  return (
    <div className={'container-fluid vh-100 pt-5 bg-login'}>
      <div className={'row mt-5'}>
        <div className={'col-lg-4 col-md-8 col-xs-12 mx-auto shadow bg-white'}>
          <form action="http://localhost:8080/user/signup" method="post" className={'px-4 py-5'}>
            <div>
              <label htmlFor="user-id" className={'form-label'}>아이디</label>
              <input type="text" className={'form-control py-3'} id="user-id" name="userAccount"
                     placeholder="아이디"/>
            </div>
            <div className={'py-4'}>
              <label htmlFor="user-pw" className={'form-label'}>비밀번호</label>
              <input type="password" className={'form-control py-3'} id="user-pw" name="userPw"
                     placeholder="비밀번호"/>
            </div>
            <div className={'py-4'}>
              <label htmlFor="user-name" className={'form-label'}>이름</label>
              <input type="text" className={'form-control py-3'} id="user-name" name="name"
                     placeholder="이름"/>
            </div>
            <div className={'py-4'}>
              <label htmlFor="user-phone" className={'form-label'}>전화번호</label>
              <input type="text" className={'form-control py-3'} id="user-phone" name="userPhone"
                     placeholder="연락처"/>
            </div>
            <div className={'d-grid gap-2'}>
              <button type="submit" className={'btn btn-primary py-3 mt-3 text-white'}>회원 등록</button>
            </div>
          </form>
        </div>
      </div>
    </div>
  )
}

export default SignUp