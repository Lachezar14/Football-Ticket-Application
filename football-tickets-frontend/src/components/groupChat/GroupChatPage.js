import * as React from 'react';
import './chatCss.css';

export default function GroupChatPage() {
    
    function connect(){}
    function onConnected(){}
    function omMessageSent(){}
    function onMessageReceived(){}
    
    
    
    
    
    return (
        <>
        <div id="username-page">
            <div className="username-page-container">
                <h1 className="title">Type your name</h1>
                <form id="usernameForm" name="usernameForm">
                    <div className="form-group">
                        <input type="text" id="name" placeholder="Nome"
                               autoComplete="off" className="form-control"/>
                    </div>
                    <div className="form-group">
                        <button type="submit" className="accent username-submit">Start talking</button>
                    </div>
                </form>
            </div>
        </div>

    <div id="chat-page" className="hidden">
        <div className="chat-container">
            <div className="chat-header">
                <h2>ChatBox</h2>
            </div>
            <div className="connecting">Conectando ao chat...</div>
            <ul id="messageArea">

            </ul>
            <form id="messageForm" name="messageForm" nameForm="messageForm">
                <div className="form-group">
                    <div className="input-group clearfix">
                        <input type="text" id="message" placeholder="Type a message..."
                               autoComplete="off" className="form-control"/>
                        <button type="submit" className="primary">Send</button>
                    </div>
                </div>
            </form>
        </div>
    </div>
        </>
    )
}