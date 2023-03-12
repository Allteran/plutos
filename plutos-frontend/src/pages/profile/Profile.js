import {Badge, Descriptions, Typography} from "antd";

export default function Profile() {

    return(
        <>
            <Typography.Title level={3} style={{padding: "10px"}}>Профіль користувача</Typography.Title>
            <Descriptions column={4} bordered>
                <Descriptions.Item labelStyle={{fontWeight: "bold", width: "15%"}} span={4} label="Ім'я та прізвище">Користувач Звичайний</Descriptions.Item>
                <Descriptions.Item labelStyle={{fontWeight: "bold", width: "15%"}} span={4} label="Електронна пошта">user@plutos.com</Descriptions.Item>
                <Descriptions.Item labelStyle={{fontWeight: "bold", width: "15%"}} span={4} label="Дата народження">21.01.2000</Descriptions.Item>
                <Descriptions.Item labelStyle={{fontWeight: "bold", width: "15%"}} span={4} label="Країна находження">Польща</Descriptions.Item>
                <Descriptions.Item labelStyle={{fontWeight: "bold", width: "15%"}} span={4} label="Агенція праці">Хумен ресурс</Descriptions.Item>
                <Descriptions.Item labelStyle={{fontWeight: "bold", width: "15%"}} span={4} label="Ставка">17.5 зл/год.</Descriptions.Item>
                <Descriptions.Item labelStyle={{fontWeight: "bold", width: "15%"}} span={4} label="Привілегії">-</Descriptions.Item>

            </Descriptions>
        </>
    )
}