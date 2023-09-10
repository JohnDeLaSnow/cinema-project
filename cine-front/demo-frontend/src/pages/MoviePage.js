import { useState, useEffect } from "react";
import useAxiosPrivate from "../hooks/useAxiosPrivate";
import { useNavigate, useLocation, useParams } from "react-router-dom";
import Navbar from "../components/Navbar";
import useAuth from "../hooks/useAuth";

const MoviePage = () => {

    const { auth } = useAuth();
    const [movie, setMovie] = useState();
    const [showings, setShowings] = useState();
    const {id}=useParams();
    const axiosPrivate = useAxiosPrivate();
    const navigate = useNavigate();
    const location = useLocation();

    const loadShowings = async () =>{
        let isMounted = true;
        const controller = new AbortController();

        const getShowings = async () => {
            try {
                const response = await axiosPrivate.get(`movies/${id}/showings`, {
                    signal: controller.signal
                });
                console.log(response.data);
                isMounted && setShowings(response.data);
            } catch (err) {
                if (err?.code !== `ERR_CANCELED`) {
                    console.error(err);
                    navigate('/login', { state: { from: location }, replace: true });
                }
            }
        }
        
        getShowings();

    }

    const onMakeReservation = async (id) => {
        let isMounted = true;
        const controller = new AbortController();
        let username = auth.username;

        const makeReservation = async () => {
            try {
                const response_tmp = await axiosPrivate.post(`reservations/save/${username}-${id}`, {
                    signal: controller.signal
                });
                console.log(response_tmp.data);
                isMounted && navigate('/successful_reservation');
            } catch (err) {
                if (err?.code !== 'ERR_CANCELED') {
                    console.error(err);
                    navigate('/login', { state: { from: location }, replace: true });
                }
            }
        }

        makeReservation();

        return () => {
            isMounted = false;
            controller.abort();
        }

    };

    const loadMovie = async () => {
        let isMounted = true;
        const controller = new AbortController();

        const getMovie = async () => {
            try {
                const response = await axiosPrivate.get(`movies/${id}`, {
                    signal: controller.signal
                });
                console.log(response.data);
                isMounted && setMovie(response.data);
            } catch (err) {
                if (err?.code !== 'ERR_CANCELED') {
                    console.error(err);
                    navigate('/login', { state: { from: location }, replace: true });
                }
            }
        }

        getMovie();

        return () => {
            isMounted = false;
            controller.abort();
        }
    };

    useEffect(
        ()=>{
            loadMovie()
            loadShowings()
        },[]);

    return (
        <div>
            <Navbar/>
            <br/>

            <section>
                <h1>{movie?.title}</h1>    
                <div className="movie-item">
                <div className="item-solo">
                    <div className="item-solo-text">
                        <img src={movie?.poster} className="Poster" alt=""/>
                    </div>
                    <div className="item-solo-text">
                        <p>Director: {movie?.director}</p>
                        <p>Duration: {movie?.duration} minutes</p>
                        <p>Genre: {movie?.genre}</p>
                    </div>
                </div>
                <h2>Description:</h2>
                <p>{movie?.description}</p>
                </div>
                <h1>Showings:</h1>
                <div className="item-solo">
                    <div className="item-solo-text">
                        {showings?.length? (
                            <table>
                                <thead>
                                    <tr>
                                        <th>State</th>
                                        <th>Date and Time</th>
                                        <th>Room</th>
                                        <th>Action</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    {showings.map((showing, index) => (
                                        <tr key={index}>
                                            <td>{showing?.state}</td>
                                            <td>{showing?.showingDate}</td>
                                            <td>Room {showing?.showingRoom}</td>
                                            <td>
                                                <button onClick={()=>onMakeReservation(showing.id)}>Make Reservation</button>
                                            </td>
                                        </tr>
                                    ))}
                                </tbody>
                            </table>
                        ) : <p><br />There are no registered showings</p>
                        }
                    </div>
                </div>
            </section>
        </div>
    )
}

export default MoviePage