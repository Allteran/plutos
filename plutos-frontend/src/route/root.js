import {Layout, Menu, Space} from "antd";
import { CalculatorOutlined, ScheduleOutlined } from "@ant-design/icons";
import {useState} from "react";
import {Link, Outlet} from "react-router-dom";

export default function Root() {
    const items = [
        {
            key: 'shiftList',
            label: <Link to={'/shifts'}>Зміни</Link>,
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
    ]

    return (

        <Layout className="layout">
            <Layout.Header style={{backgroundColor:"white"}}>
                <div className="logo">
                    <Link to={'/'}><b>PLUTOS</b></Link>
                </div>
                <Menu
                    mode="horizontal"
                    items={items}></Menu>
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