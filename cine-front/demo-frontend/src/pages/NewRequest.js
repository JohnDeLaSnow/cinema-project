import React, { useRef, useState, useEffect } from 'react';
import { faCheck, faTimes, faInfoCircle } from "@fortawesome/free-solid-svg-icons";
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import axios from 'axios';
import useAuth from "../hooks/useAuth";
import { Link } from "react-router-dom";

const DESCREPTION_REGEX = /^[A-z][A-z0-9 ]{3,23}$/;
const SAVE_REQUEST_URL = 'http://localhost:8080/api/request/save';

export default function Register() {

    const requestRef = useRef();
    const errRef = useRef();

    const { auth } = useAuth();

    const [description, setDescreption] = useState('');
    const [validDescription, setValidDescription] = useState(false);
    const [descreptionFocus, setDescreptionFocus] = useState(false);

    const [errMsg, setErrMsg] = useState('');
    const [success, setSuccess] = useState(false);

    useEffect(() => {
        requestRef.current.focus();
    }, [])

    useEffect(() => {
        setValidDescription(DESCREPTION_REGEX.test(description));
    }, [description])

    useEffect(() => {
        setErrMsg('');
    }, [description])

    const handleSubmit = async (e) => {
        e.preventDefault();
        // if button enabled with JS hack
        const v1 = DESCREPTION_REGEX.test(description);
        if (!v1) {
            setErrMsg("Invalid Entry");
            return;
        }

        const controller = new AbortController();

        const postRequest = async () => {
            try {
                const response = await axios({
                    url: SAVE_REQUEST_URL,
                    method: "POST",
                    headers: {
                        'Authorization': `Bearer ${auth?.access_token}`
                    },
                    data: {
                        "id": null,
                        "description": description,
                        "status": "REGISTERED",
                        "appUser": null
                    }
                });
                console.log(response.data);
                console.log(response.data.id);
                const secondResponse = await axios({
                    url: `http://localhost:8080/api/request/add_user`,
                    method: "PUT",
                    headers: {
                        'Authorization': `Bearer ${auth?.access_token}`
                    },
                    data: {
                        "username": auth?.username,
                        "requestId": response.data.id
                    }
                });
                console.log(secondResponse.data);
                setSuccess(true);
                setDescreption("");
            } catch (err) {
                if (!err?.response) {
                    setErrMsg('No Server Response');
                } else if (err.response?.status === 409) {
                    setErrMsg('Username Taken');
                } else {
                    setErrMsg('Registration Failed')
                }
                errRef.current.focus();
            }
        }

        postRequest();

        return () => {
            controller.abort();
        }
    }

    return (
        <>
            {success ? (
                <section>
                    <h1>Request Registered!</h1>
                    <p>
                        <br />
                        <Link to="/">Home</Link>
                    </p>
                </section>
            ) : (
                <section>
                    <p ref={errRef} className={errMsg ? "errmsg" : "offscreen"} aria-live="assertive">{errMsg}</p>
                    <h1>Submit Request</h1>
                    <form onSubmit={handleSubmit}>
                        <label htmlFor="descreption">
                            Descreption:
                            <FontAwesomeIcon icon={faCheck} className={validDescription ? "valid" : "hide"} />
                            <FontAwesomeIcon icon={faTimes} className={validDescription || !description ? "hide" : "invalid"} />
                        </label>
                        <input
                            type="text"
                            id="description"
                            ref={requestRef}
                            autoComplete='off'
                            onChange={(e) => setDescreption(e.target.value)}
                            value={description}
                            required
                            placeholder="Enter the reasoning for your request"
                            aria-invalid={validDescription ? "false" : "true"}
                            aria-describedby="uidnote"
                            onFocus={() => setDescreptionFocus(true)}
                            onBlur={() => setDescreptionFocus(false)}
                        />
                        <p id="uidnote" className={descreptionFocus && description && !validDescription ? "instructions" : "offscreen"}>
                            <FontAwesomeIcon icon={faInfoCircle} />
                            4 to 24 characters.<br />
                            Must begin with a letter.<br />
                            Letters, numbers, underscores, hyphens allowed.
                        </p>
                        <br />
                        <button disabled={!validDescription ? true : false}>
                            Submit
                        </button>
                    </form>
                    <p>
                        <br />
                        <Link to="/">Home</Link>
                    </p>
                </section >
            )}
        </>
    )
}
