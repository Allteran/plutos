import {Button, Layout, Menu} from "antd";
import {CalculatorOutlined, ScheduleOutlined, LogoutOutlined} from "@ant-design/icons";
import {useEffect, useState} from "react";
import {Navigate, NavLink, Outlet} from "react-router-dom";
import {STORAGE_KEY_LOGIN, STORAGE_KEY_TOKEN, STORAGE_KEY_USER_ID} from "../util/const";
import {validateToken} from "../util/authUtils";

export default function Root() {
    const [isLoggedIn, setLoggedIn] = useState(() => {
        if(localStorage.getItem(STORAGE_KEY_TOKEN) === null) {
            return false;
        } else {
            return true;
        }
    });
    const items = [
        {
            key: 'shiftList',
            label: <NavLink to="shifts">Зміни</NavLink>,
            icon: <ScheduleOutlined />
        },
        {
            label: 'Зареєструвати зміну',
            key: 'shiftCalc',
            icon: <CalculatorOutlined />
        },
        {
            label: 'Управління системою',
            key: 'adminSubMenu',
            icon: <CalculatorOutlined />,
            children: [
                {
                    label: 'Користувачі',
                    key: 'manageUsers',
                    icon: <ScheduleOutlined />
                }
            ]
        },
    ];

    useEffect(() => {
        if(localStorage.getItem(STORAGE_KEY_TOKEN) !== null) {
            validateToken(localStorage.getItem(STORAGE_KEY_TOKEN))
                .then(res => {
                    setLoggedIn(true);
                })
                .catch(er => {
                    setLoggedIn(false);
                })
        } else {
            setLoggedIn(false);
        }
    }, []);

    const logout = event => {
      event.preventDefault();
      localStorage.setItem(STORAGE_KEY_TOKEN, null);
      localStorage.setItem(STORAGE_KEY_LOGIN, null);
      localStorage.setItem(STORAGE_KEY_USER_ID, null);
      setLoggedIn(false);
    };

    if(!isLoggedIn) {
        return <Navigate replace to="/login" />;
    } else {
        return (

            <Layout className="layout">
                <Layout.Header style={{backgroundColor:"white"}}>
                    <div className="logo">
                        <NavLink to={'/'}><b>PLUTOS</b></NavLink>
                    </div>
                    <div className="logout-btn">
                        <Button type="link" onClick={logout} icon={<LogoutOutlined />}>Вийти</Button>
                    </div>
                    <Menu
                        mode="horizontal"
                        items={items}>
                    </Menu>

                </Layout.Header>
                <Layout>
                    <Layout.Content >
                        <div id="content">
                            <Outlet/>
                        </div>
                    </Layout.Content>
                </Layout>
            </Layout>
        )
    }
}