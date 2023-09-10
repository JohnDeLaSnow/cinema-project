import { useState, useEffect } from "react";
import useAxiosPrivate from "../hooks/useAxiosPrivate";
import { useNavigate, useLocation } from "react-router-dom";
import Navbar from "../components/Navbar";
import axios from 'axios';
import useAuth from "../hooks/useAuth";



const Users = () => {

    const { auth } = useAuth();
    const [users, setUsers] = useState();
    const axiosPrivate = useAxiosPrivate();
    const navigate = useNavigate();
    const location = useLocation();

    const [isAuthorized,setIsAuthorized] = useState(false);

    useEffect(() => {
        setIsAuthorized(auth?.roles?.find(role => role === "EMPLOYER" || role === "ADMIN"));
    }, [])

    const onDelete = async (username) => {
        await axios({
            url: `http://localhost:8080/api/users/delete/${username}`,
            method: "DELETE",
            headers: {
                'Authorization': `Bearer ${auth?.access_token}`
            },
            data: {}
        })
        loadUsers();
    };

    const loadUsers = async () => {
        let isMounted = true;
        const controller = new AbortController();

        const getUsers = async () => {
            try {
                const response = await axiosPrivate.get('users', {
                    signal: controller.signal
                });
                console.log(response.data);
                isMounted && setUsers(response.data);
            } catch (err) {
                if (err?.code !== 'ERR_CANCELED') {
                    console.error(err);
                    navigate('/login', { state: { from: location }, replace: true });
                }
            }
        }

        getUsers();

        return () => {
            isMounted = false;
            controller.abort();
        }
    };

    useEffect(() => {
        loadUsers();
    }, []);

    return (
        <article>
            <Navbar/>
            <br/>
            <section>
            <div className="container">
                <h2>List of Users</h2>
                {users?.length
                    ? (
                        <table>
                            <thead>
                                <tr>
                                    <th>Username</th>
                                    <th>Fisrt_Name</th>
                                    <th>Last_Name</th>
                                    <th>Role</th>
                                    <th>Action</th>
                                </tr>
                            </thead>
                            <tbody>
                                {users.map((user, index) => (
                                    <tr key={index}>
                                        <td>{user?.email}</td>
                                        <td>{user?.firstname}</td>
                                        <td>{user?.lastname}</td>
                                        <td>{user?.role}</td>
                                        <td>
                                            <button
                                            disabled={!isAuthorized ? true : false}
                                            >Edit</button>
                                            <button
                                                disabled={!isAuthorized ? true : false}
                                                onClick={() => onDelete(user?.email)}
                                            >Delete</button></td>
                                    </tr>
                                ))}
                            </tbody>
                        </table>                        
                    ) : <p>No users to display</p>
                }
                <button
                disabled={!isAuthorized ? true : false}
                >Add User</button>
            </div>
            </section>
        </article>
    );
};
export default Users;
