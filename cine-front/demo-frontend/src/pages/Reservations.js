import { useState, useEffect } from "react";
import useAxiosPrivate from "../hooks/useAxiosPrivate";
import { useNavigate, useLocation } from "react-router-dom";
import Navbar from "../components/Navbar";
import useAuth from "../hooks/useAuth";




const Reservations = () => {

    const { auth } = useAuth();
    const [reservations, setReservations] = useState();
    const axiosPrivate = useAxiosPrivate();
    const navigate = useNavigate();
    const location = useLocation();

    const [isAuthorized,setIsAuthorized] = useState(false);

    useEffect(() => {
        setIsAuthorized(auth?.roles?.find(role => role === "EMPLOYER" || role === "ADMIN"));
    }, [])

    const onDelete = async (id) => {
        let isMounted = true;
        const controller = new AbortController();

        const deleteReservation = async () => {
            try {
                const response_tmp = await axiosPrivate.delete(`reservations/${id}`, {
                    signal: controller.signal
                });
                const response = await axiosPrivate.get('reservations', {
                    signal: controller.signal
                });
                console.log(response.data);
                console.log(response_tmp.data);
                isMounted && setReservations(response.data);
            } catch (err) {
                if (err?.code !== 'ERR_CANCELED') {
                    console.error(err);
                    navigate('/login', { state: { from: location }, replace: true });
                }
            }
        }

        deleteReservation();

        return () => {
            isMounted = false;
            controller.abort();
        }

    };

    const loadReservations = async () => {
        let isMounted = true;
        const controller = new AbortController();

        const getReservations = async () => {
            try {
                const response = await axiosPrivate.get('reservations', {
                    signal: controller.signal
                });
                console.log(response.data);
                isMounted && setReservations(response.data);
            } catch (err) {
                if (err?.code !== 'ERR_CANCELED') {
                    console.error(err);
                    navigate('/login', { state: { from: location }, replace: true });
                }
            }
        }

        getReservations();

        return () => {
            isMounted = false;
            controller.abort();
        }
    };

    useEffect(() => {
        loadReservations();
    }, []);

    return (
        <article>
            <Navbar/>
            <br/>
            <section>
                <h2>List of Reservations</h2>
                {reservations?.length
                    ? (
                        <table>
                            <thead>
                                <tr>
                                    <th>Id</th>
                                    <th>Movie</th>
                                    <th>User</th>
                                    <th>Date</th>
                                    <th>Action</th>
                                </tr>
                            </thead>
                            <tbody>
                                {reservations.map((reservation, index) => (
                                    <tr key={index}>
                                        <td>{reservation?.id}</td>
                                        <td>{reservation?.reservedShowing?.showingMovie?.title}</td>
                                        <td>{reservation?.reservedUser?.email}</td>
                                        <td>{reservation?.reservedShowing?.showingDate}</td>
                                        <td>
                                            <button
                                                disabled={!isAuthorized ? true : false}
                                                onClick={() => onDelete(reservation?.id)}
                                            >Delete</button></td>
                                    </tr>
                                ))}
                            </tbody>
                        </table>
                    ) : <p><br />There are no registered reservations</p>
                }
                <button>Make new Reservation</button>
            </section>
        </article>
    );
};
export default Reservations;