import axios from '../api/axios';
import useAuth from './useAuth';

const useRefreshToken = () => {
    const { setAuth } = useAuth();
    const { auth } = useAuth();

    const refresh = async () => {
        const response = await axios.get('token/refresh', {
            headers: {
                'Authorization': `Bearer ${auth?.refresh_token}`
            }
        });
        setAuth(prev => {
            console.log(JSON.stringify(prev));
            console.log(response.data.access_token);
            return { ...prev, access_token: response.data.access_token }
        });
        return response.data.access_token;
    }
    return refresh;
};

export default useRefreshToken;
