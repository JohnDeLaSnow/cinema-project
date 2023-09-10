import { useState, useEffect } from "react";
import { useNavigate, useLocation } from "react-router-dom"
import useAxiosPrivate from "../hooks/useAxiosPrivate";
import Navbar from "../components/Navbar";
import useAuth from "../hooks/useAuth";

const PersonalInfo = () => {

    const { auth } = useAuth();
    const navigate = useNavigate();
    const [reservations, setReservations] = useState();
    const axiosPrivate = useAxiosPrivate();
    const location = useLocation();

    const loadReservations = async () =>{
        let isMounted = true;
        const controller = new AbortController();

        const getReservations = async () => {
            try {
                const response = await axiosPrivate.get(`users/${auth?.username}/reservations`, {
                    signal: controller.signal
                });
                console.log(response.data);
                isMounted && setReservations(response.data);
            } catch (err) {
                if (err?.code !== `ERR_CANCELED`) {
                    console.error(err);
                    navigate('/login', { state: { from: location }, replace: true });
                }
            }
        }
        
        getReservations();

    }

    useEffect(
        ()=>{
            loadReservations()
        },[]);

    return (
        <div>
        <Navbar></Navbar>
        <br/>
        <section>
            <h1>Personal Information:</h1>
            <p><b>First name:</b> {auth?.firstname}</p>
            <p><b>Last name:</b> {auth?.lastname}</p>
            <p><b>Email:</b> {auth?.username}</p>
            <br/>
            <h1>My Reservations:</h1>
            <div className="item-solo">
                    <div className="item-solo-text">
                        {reservations?.length? (
                            <table>
                                <thead>
                                    <tr>
                                        <th>Date and Time</th>
                                        <th>ID</th>
                                        <th>Movie</th>
                                        <th>Room</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    {reservations.map((reservation, index) => (
                                        <tr key={index}>
                                            <td>{reservation?.reservedShowing?.showingDate}</td>
                                            <td>{reservation?.id}</td>
                                            <td>{reservation?.reservedShowing?.showingMovie?.title}</td>
                                            <td>{reservation?.reservedShowing?.showingRoom}</td>
                                        </tr>
                                    ))}
                                </tbody>
                            </table>
                        ) : <p><br />There are no registered reservations in your name</p>
                        }
                    </div>
                </div>
        </section>
        </div>
    )
}

export default PersonalInfo