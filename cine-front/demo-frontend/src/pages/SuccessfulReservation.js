import { useNavigate } from "react-router-dom"
import Navbar from "../components/Navbar";

const SuccessfulReservation = () => {

    const navigate = useNavigate();

    return (
        <div>
        <Navbar></Navbar>
        <br/>
        <section>
            <h1>Reservation Successfully Made!</h1>
            <br />
        </section>
        </div>
    )
}

export default SuccessfulReservation