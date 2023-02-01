import React, { useState } from "react";
import { UserOutlined, LockOutlined } from "@ant-design/icons"
import { Button, Form, Input } from "antd";
import "./App.css";
import { Typography } from "antd";
import axios from "axios";

const { Title } = Typography;

function LoginForm () {
    const url = 'http://localhost:8080/auth/login';
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
        console.log('handleClick. Login: ', login);
        console.log('handleClick. Password: ', password);
        let credentials = {
            login: login,
            password: password
        }

        axios.post(url, credentials)
            .then(response => {
                console.log('Login completed:');
                console.log('Token: ', response.data.token)
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

    // handleClick(login, password) {
    //   console.log('Click!');
    //   console.log('Login: ', login);
    //   console.log('Password: ', password);
    // };


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
