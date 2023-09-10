import axios from '../api/axios';
import React, { useRef, useState, useEffect } from 'react';
import { faCheck, faTimes, faInfoCircle } from "@fortawesome/free-solid-svg-icons";
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

const FIRST_NAME_REGEX = /^[A-z][A-z]{3,23}$/;
const LAST_NAME_REGEX = /^[A-z][A-z]{3,23}$/;
const USERNAME_REGEX = /^[A-z][A-z0-9-_]{3,23}[@]{1}[A-z0-9-_]{3,23}[.]{1}[A-z]{2,3}$/;
const PASSWORD_REGEX = /^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[!@#$%]).{8,24}$/;
const REGISTER_URL = '/auth/register';

export default function Register() {

    const usernameRef = useRef();
    const errRef = useRef();

    const [username, setUsername] = useState('');
    const [validUsername, setValidUsername] = useState(false);
    const [usernameFocus, setUsernameFocus] = useState(false);

    const [firstName, setFirstName] = useState('');
    const [validFirstName, setValidFirstName] = useState(false);
    const [firstNameFocus, setFirstNameFocus] = useState(false);

    const [lastName, setLastName] = useState('');
    const [validLastName, setValidLastName] = useState(false);
    const [lastNameFocus, setLastNameFocus] = useState(false);

    const [password, setPassword] = useState('');
    const [validPassword, setValidPassword] = useState(false);
    const [passwordFocus, setPasswordFocus] = useState(false);

    const [matchPassword, setMatchPassword] = useState('');
    const [validMatchPassword, setValidMatchPassword] = useState(false);
    const [matchPasswordFocus, setMatchPasswordFocus] = useState(false);

    const [errMsg, setErrMsg] = useState('');
    const [success, setSuccess] = useState(false);

    useEffect(() => {
        usernameRef.current.focus();
    }, [])

    useEffect(() => {
        setValidUsername(USERNAME_REGEX.test(username));
    }, [username])

    useEffect(() => {
        setValidFirstName(FIRST_NAME_REGEX.test(firstName));
    }, [firstName])

    useEffect(() => {
        setValidLastName(LAST_NAME_REGEX.test(lastName));
    }, [lastName])

    useEffect(() => {
        setValidPassword(PASSWORD_REGEX.test(password));
        setValidMatchPassword(password === matchPassword);
    }, [password, matchPassword])

    useEffect(() => {
        setErrMsg('');
    }, [username, firstName, lastName, password, matchPassword])

    const handleSubmit = async (e) => {
        e.preventDefault();
        // if button enabled with JS hack
        const v1 = USERNAME_REGEX.test(username);
        const v2 = FIRST_NAME_REGEX.test(firstName);
        const v3 = LAST_NAME_REGEX.test(lastName);
        const v4 = PASSWORD_REGEX.test(password);
        if (!v1 || !v2 || !v3 || !v4) {
            setErrMsg("Invalid Entry");
            return;
        }
        try {
            const response = await (await axios({
                method: "POST",
                url: REGISTER_URL,
                headers: {},
                data: {
                    "firstname": firstName,
                    "lastname": lastName,
                    "email": username,
                    "password": password,
                }
            }));
            console.log(response.data);
            console.log(response.token);
            //console.log(response.refresh_token);
            setSuccess(true);
            setUsername('');
            setPassword('');
            setMatchPassword('');
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

    return (
        <>
            {success ? (
                <section>
                    <h1>Success!</h1>
                    <p>
                        <a href="/">Sign In</a>
                    </p>
                </section>
            ) : (
                <section>
                    <p ref={errRef} className={errMsg ? "errmsg" : "offscreen"} aria-live="assertive">{errMsg}</p>
                    <h1>Register</h1>
                    <form onSubmit={handleSubmit}>
                        <label htmlFor="username">
                            Email:
                            <FontAwesomeIcon icon={faCheck} className={validUsername ? "valid" : "hide"} />
                            <FontAwesomeIcon icon={faTimes} className={validUsername || !username ? "hide" : "invalid"} />
                        </label>
                        <input
                            type="text"
                            id="username"
                            ref={usernameRef}
                            autoComplete='off'
                            onChange={(e) => setUsername(e.target.value)}
                            value={username}
                            required
                            placeholder="Enter your username"
                            aria-invalid={validUsername ? "false" : "true"}
                            aria-describedby="uidnote"
                            onFocus={() => setUsernameFocus(true)}
                            onBlur={() => setUsernameFocus(false)}
                        />
                        <p id="uidnote" className={usernameFocus && username && !validUsername ? "instructions" : "offscreen"}>
                            <FontAwesomeIcon icon={faInfoCircle} />
                           Invalid Email.<br />
                        </p>
                        <label htmlFor='firstName'>
                            First Name:
                            <FontAwesomeIcon icon={faCheck} className={validFirstName ? "valid" : "hide"} />
                            <FontAwesomeIcon icon={faTimes} className={validFirstName || !firstName ? "hide" : "invalid"} />
                        </label>
                        <input
                            type="text"
                            id="firstName"
                            autoComplete='off'
                            onChange={(e) => setFirstName(e.target.value)}
                            value={firstName}
                            required
                            placeholder="Enter your first name"
                            aria-invalid={validFirstName ? "false" : "true"}
                            aria-describedby="uidnote"
                            onFocus={() => setFirstNameFocus(true)}
                            onBlur={() => setFirstNameFocus(false)}
                        />
                        <p id="uidnote" className={firstNameFocus && firstName && !validFirstName ? "instructions" : "offscreen"}>
                            <FontAwesomeIcon icon={faInfoCircle} />
                            4 to 24 characters.<br />
                            Must begin with a letter.<br />
                            Letters allowed.
                        </p>
                        <label htmlFor='lastName'>
                            Last Name:
                            <FontAwesomeIcon icon={faCheck} className={validLastName ? "valid" : "hide"} />
                            <FontAwesomeIcon icon={faTimes} className={validLastName || !lastName ? "hide" : "invalid"} />
                        </label>
                        <input
                            type="text"
                            id="lastName"
                            autoComplete='off'
                            onChange={(e) => setLastName(e.target.value)}
                            value={lastName}
                            required
                            placeholder="Enter your last name"
                            aria-invalid={validLastName ? "false" : "true"}
                            aria-describedby="uidnote"
                            onFocus={() => setLastNameFocus(true)}
                            onBlur={() => setLastNameFocus(false)}
                        />
                        <p id="uidnote" className={lastNameFocus && lastName && !validLastName ? "instructions" : "offscreen"}>
                            <FontAwesomeIcon icon={faInfoCircle} />
                            4 to 24 characters.<br />
                            Must begin with a letter.<br />
                            Letters allowed.
                        </p>
                        <label htmlFor="password">
                            Password
                            <FontAwesomeIcon icon={faCheck} className={validPassword ? "valid" : "hide"} />
                            <FontAwesomeIcon icon={faTimes} className={validPassword || !password ? "hide" : "invalid"} />
                        </label>
                        <input
                            type="password"
                            id="password"
                            placeholder="Enter your password"
                            onChange={(e) => setPassword(e.target.value)}
                            value={password}
                            required
                            aria-invalid={validPassword ? "false" : "true"}
                            aria-describedby="pwdnote"
                            onFocus={() => setPasswordFocus(true)}
                            onBlur={() => setPasswordFocus(false)}
                        />
                        <p id="pwdnote" className={passwordFocus && !validPassword ? "instructions" : "offscreen"}>
                            <FontAwesomeIcon icon={faInfoCircle} />
                            8 to 24 characters.<br />
                            Must include uppercase and lowercase letters, a number and a special character.<br />
                            Allowed special characters: <span aria-label="exclamation mark">!</span> <span aria-label="at symbol">@</span> <span aria-label="hashtag">#</span> <span aria-label="dollar sign">$</span> <span aria-label="percent">%</span>
                        </p>
                        <label htmlFor="confirm_pwd">
                            Confirm Password:
                            <FontAwesomeIcon icon={faCheck} className={validMatchPassword && matchPassword ? "valid" : "hide"} />
                            <FontAwesomeIcon icon={faTimes} className={validMatchPassword || !matchPassword ? "hide" : "invalid"} />
                        </label>
                        <input
                            type="password"
                            id="confirm_pwd"
                            placeholder="Confirm your password"
                            onChange={(e) => setMatchPassword(e.target.value)}
                            value={matchPassword}
                            required
                            aria-invalid={validMatchPassword ? "false" : "true"}
                            aria-describedby="confirmnote"
                            onFocus={() => setMatchPasswordFocus(true)}
                            onBlur={() => setMatchPasswordFocus(false)}
                        />
                        <p id="confirmnote" className={matchPasswordFocus && !validMatchPassword ? "instructions" : "offscreen"}>
                            <FontAwesomeIcon icon={faInfoCircle} />
                            Must match the first password input field.
                        </p>
                        <br />
                        <button disabled={!validUsername || !validFirstName || !validLastName || !validPassword || !validMatchPassword ? true : false}>
                            Sign Up
                        </button>
                    </form>
                    <p>
                        Already registered?<br />
                        <span className="line">
                            {/*put router link here*/}
                            <a href="/login">Sign In</a>
                        </span>
                    </p>
                </section >
            )}
        </>
    )
}
