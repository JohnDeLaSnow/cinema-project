import Home from './pages/Home';
import Login from "./pages/Login";
import PersonalInfo from './pages/PersonalInfo';
import Users from "./pages/Users";
import Reservations from './pages/Reservations';
import Register from "./pages/Register";
import MoviePage from "./pages/MoviePage";
import Showings from './pages/Showings';
import Layout from './pages/Layout';
import Unauthorized from './pages/Unauthorized';
import Missing from './pages/Missing';
import SuccessfulReservation from './pages/SuccessfulReservation';
import Movies from './pages/Movies';
import RequireAuth from './pages/RequireAuth';
import { Routes, Route } from 'react-router-dom';

function App() {
  return (
    <main className="App">
      <Routes>
        <Route path="/" element={<Layout />}>
          {/* public routes */}
          <Route path="login" element={<Login />} />
          <Route path="register" element={<Register />} />
          <Route path="unauthorized" element={<Unauthorized />} />

          {/* protected routes */}
          <Route element={<RequireAuth allowedRoles={['ADMIN','EMPLOYER','EMPLOYEE','USER']} />}>
            <Route path="/" element={<Home />} />
            <Route path="/personal_info" element={<PersonalInfo />} />
            <Route exact path="movies/:id" element={<MoviePage/>}/>
            <Route path="/successful_reservation" element={<SuccessfulReservation/>}/>
          </Route>

          <Route element={<RequireAuth allowedRoles={["ADMIN",'EMPLOYER','EMPLOYEE']} />}>
          <Route path="/reservations" element={<Reservations/>}/>
          <Route path="/movies" element={<Movies/>} />
            <Route path="/showings" element={<Showings/>}/>
            <Route path="/users" element={<Users/>}/>
          </Route>

          {/* catch all */}
          <Route path="*" element={<Missing />} />
        </Route>
      </Routes>
    </main>
  );
}

export default App;
