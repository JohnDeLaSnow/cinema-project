import { useNavigate } from "react-router-dom";
import { useContext, useState, useEffect } from "react";
import AuthContext from "../context/AuthProvider";
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faHome, faUserCircle, faDoorOpen} from "@fortawesome/free-solid-svg-icons";
import useAuth from "../hooks/useAuth";
import Reservations from "../pages/Reservations";

const Navbar = () => {

    const navigate = useNavigate();
    const { setAuth } = useContext(AuthContext);
    const { auth } = useAuth();

    const [isAuthorized,setIsAuthorized] = useState(false);

    useEffect(() => {
        setIsAuthorized(auth?.roles?.find(role => role === "EMPLOYEE" || role === "EMPLOYER" || role === "ADMIN"));
    }, [])

    const logout = async () => {
        // if used in more components, this should be in context 
        // axios to /logout endpoint 
        setAuth({});
        navigate('/login');
    }

    const homePage = async () => {
        navigate('/')
    }

    const usersPage = async () => {
        navigate('/users')
    }

    const moviesPage = async () => {
        navigate('/movies')
    }

    const showingsPage = async () => {
        navigate('/showings')
    }

    const reservationsPage = async () => {
        navigate('/reservations')
    }

    const personalInfo = async () => {
        navigate('/personal_info')
    }

    return (
        <div >
        <div className="navbar">
            <button onClick={homePage} className="navbarlogo">
                <FontAwesomeIcon icon={faHome} />
                </button>
            <button disabled={!isAuthorized ? true : false} onClick={moviesPage} className="navbarbutton">Manage Movies</button>
            <button disabled={!isAuthorized ? true : false} onClick={usersPage} className="navbarbutton">Manage users</button>
            <button disabled={!isAuthorized ? true : false} onClick={showingsPage} className="navbarbutton">Manage showings</button>
            <button disabled={!isAuthorized ? true : false} onClick={reservationsPage} className="navbarbutton" >Manage Reservations</button>
            <button onClick={personalInfo} className="navbarbutton">
                <FontAwesomeIcon icon={faUserCircle} />   {auth?.username}
            </button>
            <button className="navbarbutton" onClick={logout}><FontAwesomeIcon icon={faDoorOpen}/> Logout</button>
        </div>
        </div>
    )
}

export default Navbar