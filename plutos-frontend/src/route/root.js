import {Layout, Menu} from "antd";
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

    const [current, setCurrent] = useState('shiftList');

    const onClick = e => {
        console.log('click', e);
        setCurrent(e.key);
    }


    return (
        <>
            <Layout className="layout">
                <Layout.Header style={{backgroundColor:"white"}}>
                    <div className="logo" />
                    <Menu
                        mode="horizontal"
                        defaultSelectedKeys={[current]}
                        items={items}></Menu>
                    {/*<Menu*/}
                    {/*    mode="horizontal"*/}
                    {/*    items={items}*/}
                    {/*    selectedKeys={[current]}*/}
                    {/*    onClick={onClick}/>*/}
                </Layout.Header>
                <Layout>
                    <Layout.Content >
                        <div id="content">
                            <Outlet/>
                        </div>
                    </Layout.Content>
                </Layout>
            </Layout>
            {/*<Menu onClick={onClick} selectedKeys={[current]} mode="horizontal" items={items}>*/}
            {/*</Menu>*/}
        </>
    )
}