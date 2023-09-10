import { useRef, useState, useEffect } from 'react';
import useAuth from '../hooks/useAuth';
import { Link, useNavigate, useLocation } from 'react-router-dom';

import axios from '../api/axios';
const LOGIN_URL = '/auth/authenticate';

export default function Login() {

    const { setAuth } = useAuth();

    const navigate = useNavigate();
    const location = useLocation();
    const from = location.state?.from?.pathname || "/";

    const userRef = useRef();
    const errRef = useRef();

    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');
    const [errMsg, setErrMsg] = useState('');

    useEffect(() => {
        userRef.current.focus();
    }, [])

    useEffect(() => {
        setErrMsg('');
    }, [username, password])

    const handleSubmit = async (e) => {
        e.preventDefault();

        try {
            const response = await (await axios({
                method: "POST",
                url: LOGIN_URL,
                headers: {},
                data: {
                    "email": username,
                    "password": password,
                }
            }));
            console.log(response);

            const access_token = response?.data?.token;
            //const refresh_token = response?.data?.refresh_token;
            console.log(access_token);
            const user = await axios.get(`/users/${username}`, {
                headers: {
                    'Authorization': `Bearer ${access_token}`
                }
            })
            const roles = [user?.data?.role];
            const firstname =user?.data?.firstname;
            const lastname =user?.data?.lastname;
            console.log(roles);
            setAuth({ username, firstname, lastname, password, roles, access_token, });
            setUsername('');
            setPassword('');
            navigate(from, { replace: true });
        } catch (err) {
            if (!err?.response) {
                console.log(process.env.REACT_APP_BACKEND_URL);
                setErrMsg('No Server Response');
            } else if (err.response?.status === 401) {
                setErrMsg('Invalid Credentials');
            } else {
                setErrMsg('Login Failed');
            }
            errRef.current.focus();
        }
    }

    return (
        <section>
            <p ref={errRef} className={errMsg ? "errmsg" : "offscreen"} aria-live="assertive">{errMsg}</p>
            <h1>Sign in</h1>
            <form onSubmit={handleSubmit}>
                <label htmlFor="username">Email:</label>
                <input
                    type="text"
                    id="username"
                    ref={userRef}
                    autoComplete='off'
                    onChange={(e) => setUsername(e.target.value)}
                    value={username}
                    required
                    placeholder="Enter your username"
                />
                <label htmlFor="password">Password:</label>
                <input
                    type="password"
                    id="password"
                    onChange={(e) => setPassword(e.target.value)}
                    value={password}
                    required
                    placeholder="Enter your password"
                />
                <button>Sign in</button>
            </form>
            <p>
                Need an acount?<br />
                <span className="line">
                    <Link to="/register">Sign Up</Link>
                </span>
            </p>
        </section >
    )
}
