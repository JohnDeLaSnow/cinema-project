import { useState, useEffect } from "react";
import useAxiosPrivate from "../hooks/useAxiosPrivate";
import { useNavigate, useLocation } from "react-router-dom";
import Navbar from "../components/Navbar";
import useAuth from "../hooks/useAuth";




const Showings = () => {

    const { auth } = useAuth();
    const [showings, setShowings] = useState();
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

        const deleteShowing = async () => {
            try {
                const response_tmp = await axiosPrivate.delete(`showings/${id}`, {
                    signal: controller.signal
                });
                const response = await axiosPrivate.get('showings', {
                    signal: controller.signal
                });
                console.log(response.data);
                console.log(response_tmp.data);
                isMounted && setShowings(response.data);
            } catch (err) {
                if (err?.code !== 'ERR_CANCELED') {
                    console.error(err);
                    navigate('/login', { state: { from: location }, replace: true });
                }
            }
        }

        deleteShowing();

        return () => {
            isMounted = false;
            controller.abort();
        }

    };

    const loadShowings = async () => {
        let isMounted = true;
        const controller = new AbortController();

        const getShowings = async () => {
            try {
                const response = await axiosPrivate.get('showings', {
                    signal: controller.signal
                });
                console.log(response.data);
                isMounted && setShowings(response.data);
            } catch (err) {
                if (err?.code !== 'ERR_CANCELED') {
                    console.error(err);
                    navigate('/login', { state: { from: location }, replace: true });
                }
            }
        }

        getShowings();

        return () => {
            isMounted = false;
            controller.abort();
        }
    };

    useEffect(() => {
        loadShowings();
    }, []);

    return (
        <article>
            <Navbar/>
            <br/>
            <section>
                <h2>List of Showings</h2>
                {showings?.length
                    ? (
                        <table>
                            <thead>
                                <tr>
                                    <th>Id</th>
                                    <th>Movie</th>
                                    <th>State</th>
                                    <th>Date and Time</th>
                                    <th>Room</th>
                                    <th>Action</th>
                                </tr>
                            </thead>
                            <tbody>
                                {showings.map((showing, index) => (
                                    <tr key={index}>
                                        <td>{showing?.id}</td>
                                        <td>{showing?.showingMovie.title}</td>
                                        <td>{showing?.state}</td>
                                        <td>{showing?.showingDate}</td>
                                        <td>Room {showing?.showingRoom}</td>
                                        <td>
                                            <button
                                            disabled={!isAuthorized ? true : false}
                                                onClick={() => onDelete(showing?.id)}
                                            >Delete</button></td>
                                    </tr>
                                ))}
                            </tbody>
                        </table>
                    ) : <p><br />There are no registered showings</p>
                }
                <button
                disabled={!isAuthorized ? true : false}
                >Add Showing</button>
            </section>
        </article>
    );
};
export default Showings;
