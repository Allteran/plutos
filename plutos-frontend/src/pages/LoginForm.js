import React, { useState } from "react";
import { UserOutlined, LockOutlined } from "@ant-design/icons"
import { Button, Form, Input } from "antd";
import { Typography } from "antd";
import axios from "axios";
import {STORAGE_KEY_TOKEN, STORAGE_KEY_LOGIN, URL_LOGIN, STORAGE_KEY_USER_ID} from "../util/const";
import { useNavigate } from "react-router-dom";

const { Title } = Typography;


function LoginForm () {
    const navigate = useNavigate();
    const [login, setLogin] = useState('');
    const [password, setPassword] = useState('');

    const handleLoginChange = event => {
        setLogin(event.target.value);
    };

    const handleChangePassword = event => {
        setPassword(event.target.value);
    }

    const handleClick = event => {
        event.preventDefault();
        let credentials = {
            login: login,
            password: password
        }

        axios.post(URL_LOGIN, credentials)
            .then(response => {
                localStorage.setItem(STORAGE_KEY_TOKEN, response.data.token);
                localStorage.setItem(STORAGE_KEY_LOGIN, response.data.login);
                localStorage.setItem(STORAGE_KEY_USER_ID, response.data.userId);
                navigate("/");
                console.log('login = ', response.data.login);
                console.log('userId = ', response.data.userId);

            })
    };

    const onFinish = (values) => {
        console.log('Successful login: ', values);
    };

    const onFinishFailed =(errorMessage) => {
        console.log('Failed: ', errorMessage);
    };

    const emailRules = [
        {
            required: true,
            message: 'Please input your username!',
        },
    ];

    const passwordRules = [
        {
            required: true,
            message: 'Please input your password!',
        },
    ];

    return (
        <div>
            <Form onFinish={onFinish} onFinishFailed={onFinishFailed} className="login-form">
                <Form.Item rules={emailRules}>
                    <Title className="login-title" level={3}>PLUTOS | Авторизація</Title>

                    <Input
                        name="login"
                        id="login"
                        onChange={handleLoginChange}
                        prefix={<UserOutlined className="site-form-item-icon" />}
                        placeholder="E-Mail" />
                </Form.Item>
                <Form.Item rules={passwordRules}>

                    <Input.Password
                        id="password"
                        name="password"
                        onChange={handleChangePassword}
                        prefix={<LockOutlined className="site-form-item-icon" />}
                        type="password"
                        placeholder="Пароль"/>
                </Form.Item>
                <Form.Item>
                    <Button
                        onClick={handleClick}
                        type="primary"
                        htmlType="submit"
                        className="login-form-button"
                    >
                        Увійти
                    </Button>
                </Form.Item>
            </Form>
        </div>
    )
}

export default LoginForm;
