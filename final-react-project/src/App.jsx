
// import {BrowserRouter, Route, Routes} from "react-router-dom";
import TempComponent from "./components/temp/TempComponent.jsx";
import {BrowserRouter, Route, Routes} from "react-router-dom";
// import Login from "./components/test/Login.jsx";
import LoginTest from "./components/test/LoginTest.jsx";
import SignUp from "./components/test/SignUp.jsx";
import LoginFail from "./components/test/LoginFail.jsx";
import Login from "./components/test/Login.jsx";

function App() {

  return (
    <div>
      <BrowserRouter>
        <Routes >
          <Route path={'/login'} element={<Login />}/>
          <Route path={'/loginsuccess'} element={<LoginTest />} />
          <Route path={'/signup'} element={<SignUp />}/>
          <Route path={'/loginfail'} element={<LoginFail />} />
        </Routes>
      </BrowserRouter>
    </div>
  );
}

export default App;
