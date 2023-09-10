import { useState, useEffect } from "react";
import useAxiosPrivate from "../hooks/useAxiosPrivate";
import { useNavigate, useLocation } from "react-router-dom";
import Navbar from "../components/Navbar";
import useAuth from "../hooks/useAuth";




const Movies = () => {

    const { auth } = useAuth();
    const [movies, setMovies] = useState();
    const axiosPrivate = useAxiosPrivate();
    const navigate = useNavigate();
    const location = useLocation();

    const [isAuthorized,setIsAuthorized] = useState(false);

    useEffect(() => {
        setIsAuthorized(auth?.roles?.find(role => role === "EMPLOYER" || role === "ADMIN"));
    }, [])

    const onDelete = async (title) => {
        let isMounted = true;
        const controller = new AbortController();

        const deleteMovie = async () => {
            try {
                const response_tmp = await axiosPrivate.delete(`movies/${title}`, {
                    signal: controller.signal
                });
                const response = await axiosPrivate.get('movies', {
                    signal: controller.signal
                });
                console.log(response.data);
                console.log(response_tmp.data);
                isMounted && setMovies(response.data);
            } catch (err) {
                if (err?.code !== 'ERR_CANCELED') {
                    console.error(err);
                    navigate('/login', { state: { from: location }, replace: true });
                }
            }
        }

        deleteMovie();

        return () => {
            isMounted = false;
            controller.abort();
        }

    };

    const loadMovies = async () => {
        let isMounted = true;
        const controller = new AbortController();

        const getMovies = async () => {
            try {
                const response = await axiosPrivate.get('movies', {
                    signal: controller.signal
                });
                console.log(response.data);
                isMounted && setMovies(response.data);
            } catch (err) {
                if (err?.code !== 'ERR_CANCELED') {
                    console.error(err);
                    navigate('/login', { state: { from: location }, replace: true });
                }
            }
        }

        getMovies();

        return () => {
            isMounted = false;
            controller.abort();
        }
    };

    useEffect(() => {
        loadMovies();
    }, []);

    return (
        <article>
            <Navbar/>
            <br/>
            <section>
                <h2>List of Movies</h2>
                {movies?.length
                    ? (
                        <table>
                            <thead>
                                <tr>
                                    <th>Poster</th>
                                    <th>Title</th>
                                    <th>Director</th>
                                    <th>Genre</th>
                                    <th>Duration</th>
                                    <th>Action</th>
                                </tr>
                            </thead>
                            <tbody>
                                {movies.map((movie, index) => (
                                    <tr key={index}>
                                        <td><img src={movie?.poster} className="Poster" alt=""/></td>
                                        <td>{movie?.title}</td>
                                        <td>{movie?.director}</td>
                                        <td>{movie?.genre}</td>
                                        <td>{movie?.duration} minutes</td>
                                        <td>
                                            <button
                                                disabled={!isAuthorized ? true : false}
                                                onClick={() => onDelete(movie?.title)}
                                            >Delete</button></td>
                                    </tr>
                                ))}
                            </tbody>
                        </table>
                    ) : <p><br />There are no registered movies</p>
                }
                <button
                disabled={!isAuthorized ? true : false}
                >Add Movie</button>
            </section>
        </article>
    );
};
export default Movies;
