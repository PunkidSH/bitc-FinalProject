
// import {BrowserRouter, Route, Routes} from "react-router-dom";
import TempComponent from "./components/temp/TempComponent.jsx";
import {BrowserRouter, Route, Routes} from "react-router-dom";
import Login from "./components/test/Login.jsx";
import LoginTest from "./components/test/LoginTest.jsx";
import SignUp from "./components/test/SignUp.jsx";

function App() {

  return (
    <div>
      <BrowserRouter>
        <Routes >
          <Route path={'/'} element={<TempComponent />}/>
          <Route path={'/loginsuccess'} element={<LoginTest />} />
          <Route path={'/signup'} element={<SignUp />}/>
        </Routes>
      </BrowserRouter>
    </div>
  );
}

export default App;
