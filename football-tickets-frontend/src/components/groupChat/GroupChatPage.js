import * as React from 'react';
import {over} from "stompjs";
import SockJs from "sockjs-client";
import api from "../../services/api";
import {colors} from "@mui/material";  
import {useEffect, useState} from "react";
import TextField from "@mui/material/TextField";
import Button from "@mui/material/Button";
import {Box} from "@mui/system";
import Typography from "@mui/material/Typography";
import Container from "@mui/material/Container";
import Paper from "@mui/material/Paper";

let stompClient = null;
export default function GroupChatPage() {
    
    const user = api.getCurrentUser();
    const first_name = "john";

    const [eventMessage, setEventMessage] = useState([]);
    const [chatMessage, setChatMessage] = useState([]);
    const [tab,setTab] =useState("CHATROOM");
    const [message, setMessage] = useState('');
    const [userData, setUserData] = useState({
        sender: '',
        connected: false,
        content: ''
    });

    useEffect(() => {
        console.log(eventMessage);
    }, [eventMessage]);
    useEffect(() => {
        console.log(chatMessage);
    }, [chatMessage]);
    
    
    useEffect(() => {
        console.log(userData);
    }, [userData]);

    const connect =()=>{
        var socket = new SockJs('http://localhost:8080/websocket');
        stompClient = over(socket);
        stompClient.connect({},onConnected, onError);
    }

    const onConnected = () => {
        setUserData({...userData,"connected": true});
        stompClient.subscribe('/topic/public', onMessageReceived);
        userJoin();
    }

    const userJoin=()=>{
        stompClient.send("/app/chat.register",
            {},
            JSON.stringify({sender: user.first_name, type: 'JOIN'})
        )
    }

    const onMessageReceived = (payload)=> {
        let message = JSON.parse(payload.body);
        if (message.type === 'JOIN') {
            eventMessage.push(message);
            message.content = message.sender + ' joined!';
            setEventMessage([...eventMessage]);
        } else if (message.type === 'LEAVE') {
            eventMessage.push(message);
            message.content = message.sender + ' left!';
            setEventMessage([...eventMessage]);
        } else {
            chatMessage.push(message);
            setChatMessage([...chatMessage]);
        }
    }

    const onMessageSend = ()=> {
        if (stompClient) {
            const chatMessage = {
                sender: user.first_name,
                content: message,
                type: "CHAT"
            };
            console.log(chatMessage);
            stompClient.send("/app/chat.send", {}, JSON.stringify(chatMessage));
            //setUserData({...userData, "message": ""});
            setMessage('');
        }
    }
    
    const clearMessage = () => {
        setMessage('');
    }

    const onError = (err) => {
        console.log(err);

    }

    const handleMessage =(event)=>{
        const {value}=event.target;
        setMessage(value);
    }
    
    return (
        <>
            {stompClient === null ?
                <Box sx={{display:'flex',justifyContent:"center",alignItems:'center'}}>
                    <Container component="main" maxWidth="md" sx={{mb: 4}}>
                        <Paper variant="outlined" sx={{boxShadow: 3 ,my: { xs: 3, md: 6 }, p: { xs: 2, md: 3 } }}>
                            <Box sx={{display:'flex',flexDirection:'column',alignItems:'center'}}>
                                <Typography variant="h4" sx={{mt: 5}}>Join a group chat with other football fans</Typography>
                                <Button
                                    sx={{mt: 5,mb: 3}}
                                    variant="contained"
                                    onClick={connect}>Start talking
                                </Button>
                            </Box>
                        </Paper>
                    </Container>
                </Box>
                :
                <Box sx={{display:'flex',justifyContent:"center",mt:27}}>
                    <Container component="main" maxWidth="md" sx={{mb: 4,}}>
                        <Paper sx={{boxShadow:3}}>
                            <Box sx={{display:'flex',justifyContent:'center',border:1,borderColor:'grey.200'}}>
                                <Typography variant="h4" sx={{mt: 2,mb:3}}>Chatbox</Typography>
                            </Box>
                            <ul className="messageArea">
                                {eventMessage.map((message, index) => (
                                    <li key={index} className="message">
                                        <div className="message-content">
                                            <p>{message.content}</p>
                                        </div>
                                    </li>
                                ))}
                                {chatMessage.map((chat, index) => (
                                    <li key={index} className="message">
                                        <div className="message-username">{chat.sender}</div>
                                        <div className="message-text">{chat.content}</div>
                                    </li>
                                ))}
                            </ul>
                            <Box sx={{display:'flex',justifyContent:'flex-end',alignItems:'baseline'}}>
                                <TextField
                                    fullWidth
                                    name="message"
                                    label="Type a message..."
                                    variant="outlined"
                                    value={message}
                                    onChange={handleMessage}
                                    onClick={clearMessage}
                                />
                                <Button
                                    type="submit"
                                    variant="contained"
                                    onClick={onMessageSend}>Send</Button>
                            </Box>
                        </Paper>
                    </Container>
                </Box>
            }
        </>
    )
}



{/*
<div id="username-page">
    <div className="username-page-container">
        <h1 className="title">Type your name</h1>
        <div className="form-group">
            <button type="button" className="accent username-submit" onClick={connect}>Start talking</button>
        </div>
    </div>
</div>


<div id="chat-page">
    <div className="chat-container">
        <div className="chat-header">
            <h2>ChatBox</h2>
        </div>
        <div className="connecting">Connecting to chat...</div>
        <ul className="messageArea">
            {chatMessage.map((message, index) => {
                return (
                    <li key={index} className="message">
                        <div className="message-content">
                            <span className="message-username">{message.sender}</span>
                            <span className="message-text">{message.message}</span>
                        </div>
                    </li>
                )
            })}
        </ul>
        <form id="messageForm" name="messageForm">
            <div className="form-group">
                <div className="input-group clearfix">
                    <input type="text" id="message" placeholder="Type a message..."
                           autoComplete="off" className="form-control"/>
                    <button type="button" className="primary" onClick={onMessageSend}>Send</button>
                </div>
            </div>
        </form>
    </div>
</div>
*/}

{/*
  <Box sx={{display:'flex',justifyContent:"center",alignItems:'center'}}>
                <Container component="main" maxWidth="md" sx={{mb: 4}}>
                    <Paper variant="outlined" sx={{boxShadow: 3 ,my: { xs: 3, md: 6 }, p: { xs: 2, md: 3 } }}>
                        <Box sx={{display:'flex',flexDirection:'column',alignItems:'center'}}>
                            <Typography variant="h4" sx={{mt: 5}}>Join a group chat with other football fans</Typography>
                            <Button
                                sx={{mt: 5,mb: 3}}
                             variant="contained"
                             onClick={connect}>Start talking
                            </Button>
                        </Box>
                    </Paper>
                </Container>
                </Box>
                :
                <Box sx={{display:'flex',justifyContent:"center",mt:27}}>
                <Container component="main" maxWidth="md" sx={{mb: 4,}}>
                    <Paper sx={{boxShadow:3}}>
                        <Box className="chat-header">
                            <Typography variant="h4" sx={{mt: 2,mb:3}}>Chatbox</Typography>
                        </Box>
                        <ul className="messageArea">
                            {eventMessage.map((message, index) => (
                                <li key={index} className="message">
                                    <div className="message-content">
                                        <p>{message.content}</p>
                                    </div>
                                </li>
                            ))}
                            {chatMessage.map((chat, index) => (
                                <li key={index} className="message">
                                    <div className="message-username">{chat.sender}</div>
                                    <div className="message-text">{chat.content}</div>
                                </li>
                            ))}
                        </ul>
                        <Box sx={{display:'flex',justifyContent:'flex-end',alignItems:'baseline'}}>
                            <TextField 
                                fullWidth
                                name="message" 
                                label="Type a message..." 
                                variant="outlined"
                                onChange={handleMessage}/>
                            <Button
                                type="submit"
                                variant="contained"
                                onClick={onMessageSend}>Send</Button>
                        </Box>
                    </Paper>
                </Container>
                </Box>
*/}