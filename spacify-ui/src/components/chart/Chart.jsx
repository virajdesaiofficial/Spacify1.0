import React, {useEffect, useState} from 'react';
import {Button} from "react-bootstrap";
import { BarChart, Bar, XAxis, YAxis, CartesianGrid, Tooltip, Legend, ResponsiveContainer} from "recharts";
import {ROOM_TREND_API} from "../../endpoints";

function Chart(props) {

  return ( props.trend.length > 0) ? (

<div>
    <BarChart
        width={500} height={300}
        data={props.trend}
        margin={{
            top: 5,
            right: 30,
            left: 20,
            bottom: 5,
        }}>
        <CartesianGrid strokeDasharray="3 10" />
        <XAxis dataKey="string1" />
        <YAxis dataKey="string2" />
        <Tooltip />
        <Legend />
           <Bar dataKey="string2" name="occupancy" fill="#8884d8" />
    </BarChart>
</div>

  ) : "";
}

export default Chart;