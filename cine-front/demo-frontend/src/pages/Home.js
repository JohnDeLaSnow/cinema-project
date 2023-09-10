import { useState, useEffect } from "react";
import useAxiosPrivate from "../hooks/useAxiosPrivate";
import { useNavigate, useLocation } from "react-router-dom";
import Navbar from "../components/Navbar";

const Home = () => {

    const [movies, setMovies] = useState();
    const axiosPrivate = useAxiosPrivate();
    const navigate = useNavigate();
    const location = useLocation();

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

    useEffect(
        ()=>{
            loadMovies()
        },[]);

    return (
        <div>
            <Navbar/>
            <br/>
                <section>
                <h1>Playing now:</h1>
                {movies?.length?
                (
                    <div className="item-list">
                        {movies.map((movie) => (
                                <div className="item" key={movie.title} onClick={()=>{navigate(`/movies/${movie.title}`)}}>
                                    <img src={movie?.poster} className="Poster" alt=""/>
                                    <p>{movie?.title}</p>
                                    </div>
                                ))}
                    </div>
                ) : <p><br />There are no registered movies</p>
                }
                </section>
        </div>
    )
}

export default Home