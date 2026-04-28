const chatMessages = document.getElementById('chatMessages');
const chatForm = document.getElementById('chatForm');
const userMessageInput = document.getElementById('userMessage');
const sendBtn = document.getElementById('sendBtn');

chatForm.addEventListener('submit', async (e) => {
    e.preventDefault();
    
    const userMessage = userMessageInput.value.trim();
    if (!userMessage) return;
    
    addMessage(userMessage, 'user');
    userMessageInput.value = '';
    
    sendBtn.disabled = true;
    const typingIndicator = addTypingIndicator();
    
    try {
        const response = await fetch('/api/chat', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({ message: userMessage })
        });
        
        const data = await response.json();
        removeTypingIndicator(typingIndicator);
        addMessage(data.response, 'bot');
        
    } catch (error) {
        removeTypingIndicator(typingIndicator);
        addMessage('Sorry, I encountered an error. Please try again.', 'bot');
        console.error('Error:', error);
    } finally {
        sendBtn.disabled = false;
        userMessageInput.focus();
    }
});

function addMessage(text, sender) {
    const messageDiv = document.createElement('div');
    messageDiv.className = `message ${sender}-message`;
    messageDiv.textContent = text;
    chatMessages.appendChild(messageDiv);
    chatMessages.scrollTop = chatMessages.scrollHeight;
}

function addTypingIndicator() {
    const typingDiv = document.createElement('div');
    typingDiv.className = 'typing-indicator';
    typingDiv.innerHTML = '<span></span><span></span><span></span>';
    chatMessages.appendChild(typingDiv);
    chatMessages.scrollTop = chatMessages.scrollHeight;
    return typingDiv;
}

function removeTypingIndicator(indicator) {
    if (indicator && indicator.parentNode) {
        indicator.parentNode.removeChild(indicator);
    }
}
