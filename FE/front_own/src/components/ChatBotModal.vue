<template>
  <div class="chatbot-wrapper">
    <button class="ai-trigger-btn" @click="isModalOpen = true">
      <span class="ai-icon">✨</span>
      <span class="btn-text">AI 코치 (Pro)</span>
    </button>

    <Teleport to="body">
      <div v-if="isModalOpen" class="modal-overlay" @click.self="isModalOpen = false">
        <div class="chat-modal glass-card">
          <div class="chat-header pro-version">
            <div class="header-content">
              
              <div>
                <h3>OWN AI ASSISTANT <span class="badge"></span></h3>
                <p class="status">추론 중에는 시간이 소요될 수 있습니다</p>
              </div>
            </div>
            <button class="close-btn" @click="isModalOpen = false">✕</button>
          </div>

          <div class="chat-messages" ref="chatBox">
            <div v-for="(msg, i) in messages" :key="i" :class="['msg-row', msg.role]">
              <div class="bubble-wrapper">
                <div class="bubble">
                  {{ msg.content }}
                </div>
              </div>
            </div>
            <div v-if="isLoading" class="msg-row assistant">
              <div class="bubble-wrapper">
                <div class="bubble loading">
                  <span class="dot-flashing"></span>
                  AI가 복잡한 문제를 해결하고 있습니다...
                </div>
              </div>
            </div>
          </div>

          <div class="chat-input-area">
            <input 
              v-model="userInput" 
              @keyup.enter="handleSend" 
              :disabled="isLoading"
            />
            <button @click="handleSend" :disabled="isLoading || !userInput.trim()">
              {{ isLoading ? '...' : '전송' }}
            </button>
          </div>
        </div>
      </div>
    </Teleport>
  </div>
</template>

<script setup>
import { ref, nextTick } from 'vue';

const isModalOpen = ref(false);
const userInput = ref('');
const isLoading = ref(false);
const chatBox = ref(null);

// 1. 발급받으신 GMS KEY
const GMS_KEY = 'S14P02AM05-a79efcde-e514-4d74-9522-cb5cecacc1be';

const messages = ref([
  { role: 'assistant', content: '안녕하세요! OWN 어시스턴트입니다!' }
]);

const handleSend = async () => {
  if (!userInput.value.trim() || isLoading.value) return;

  const text = userInput.value;
  messages.value.push({ role: 'user', content: text });
  userInput.value = '';
  isLoading.value = true;

  await scrollToBottom();

  try {
    // 2. GMS GPT-5.2-pro 전용 엔드포인트
    const GMS_ENDPOINT = '/gmsapi/api.openai.com/v1/responses';

    // 3. 다중 턴 상호작용을 위해 메시지 기록을 하나의 문자열로 결합 (input 필드용)
    const promptContext = messages.value.map(m => `${m.role === 'user' ? 'User' : 'Assistant'}: ${m.content}`).join('\n');
    const finalInput = `당신은 운동과 어울리는 음악을 추천해주는 전문가입니다. 다음 대화 맥락을 보고 친절하게 답변해주세요. 마크다운 형식말고 실제 어시스턴트가 클라이언트에게 답변해주듯 구어체로 답변해주세요. 존댓말로 답변해주세요.\n\n${promptContext}\nAssistant:`;

    const response = await fetch(GMS_ENDPOINT, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
        'Authorization': `Bearer ${GMS_KEY}`
      },
      body: JSON.stringify({
        model: "gpt-5.2-pro",
        input: finalInput // gpt-5.2-pro는 'input' 키를 사용합니다.
      })
    });

    if (!response.ok) throw new Error('API 호출 실패');

    const data = await response.json();
    
    // OpenAI API 표준 파싱

    let aiResponse = '응답을 불러오지 못했습니다.';

    if (data && Array.isArray(data.output)) {
  const assistantMsg = data.output.find(
    msg => msg.role === 'assistant'
  );

  if (assistantMsg && Array.isArray(assistantMsg.content)) {
    const outputText = assistantMsg.content.find(
      c => c.type === 'output_text'
    );

    if (outputText?.text) {
      aiResponse = outputText.text;
    }
  }
}
    messages.value.push({
      role: 'assistant',
      content: aiResponse
    });

  } 
  
  catch (e) {
    console.error(e);
    messages.value.push({ role: 'assistant', content: '복잡한 요청으로 인해 시간이 초과되었거나 연결에 실패했습니다.' });
  } finally {
    isLoading.value = false;
    await scrollToBottom();
  }
};

const scrollToBottom = async () => {
  await nextTick();
  if (chatBox.value) chatBox.value.scrollTop = chatBox.value.scrollHeight;
};
</script>

<style scoped>
/* 사이드바 트리거 버튼 - 작성하기 버튼과 유사한 컨셉 */
.ai-trigger-btn {
  width: 100%;
  padding: 12px;
  background: rgba(255, 255, 255, 0.1);
  border: 1.5px solid rgba(255, 255, 255, 0.2);
  border-radius: 12px;
  color: #fff;
  font-weight: 600;
  cursor: pointer;
  display: flex;
  align-items: center;
  justify-content: center;
  gap: 10px;
  transition: all 0.3s;
}

.ai-trigger-btn:hover {
  background: rgba(255, 255, 255, 0.15);
  border-color: #E1603F;
}

/* 모달 오버레이 */
.modal-overlay {
  position: fixed;
  top: 0; left: 0; width: 100vw; height: 100vh;
  background: rgba(0, 0, 0, 0.5);
  backdrop-filter: blur(8px);
  z-index: 9999;
  display: flex;
  justify-content: center;
  align-items: center;
}

/* 챗 모달 본체 - Dark Glassmorphism */
.chat-modal {
  width: 420px;
  height: 600px;
  background: rgba(28, 28, 30, 0.95);
  border-radius: 30px;
  border: 1px solid rgba(255, 255, 255, 0.1);
  display: flex;
  flex-direction: column;
  overflow: hidden;
  box-shadow: 0 25px 50px rgba(0,0,0,0.5);
}

/* 헤더: 사이드바의 Gradient 테마 공유 */
.chat-header {
  padding: 24px;
  background: linear-gradient(135deg, #2E3781 0%, #E1603F 100%);
  color: white;
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.header-content { display: flex; align-items: center; gap: 12px; }
.bot-avatar-main {
  width: 40px; height: 40px; background: rgba(255,255,255,0.2);
  border-radius: 50%; display: flex; align-items: center; justify-content: center;
  font-weight: bold; border: 1px solid rgba(255,255,255,0.4);
}

.chat-header h3 { font-size: 1.1rem; margin: 0; }
.status { font-size: 0.75rem; opacity: 0.8; margin: 0; }
.close-btn { background: none; border: none; color: white; font-size: 1.2rem; cursor: pointer; }

/* 채팅 내용 영역 */
.chat-messages {
  flex: 1;
  overflow-y: auto;
  padding: 20px;
  display: flex;
  flex-direction: column;
  gap: 15px;
  background: rgba(0,0,0,0.1);
}

.msg-row { display: flex; width: 100%; }
.msg-row.user { justify-content: flex-end; }

.bubble {
  max-width: 280px;
  padding: 12px 16px;
  border-radius: 20px;
  font-size: 0.95rem;
  line-height: 1.5;
  white-space: pre-line;
}

.assistant .bubble {
  background: rgba(255, 255, 255, 0.1);
  color: #fff;
  border-bottom-left-radius: 4px;
  border: 1px solid rgba(255,255,255,0.05);
}

.user .bubble {
  background: #fff;
  color: #1c1c1e;
  border-bottom-right-radius: 4px;
  font-weight: 500;
}

/* 입력창 디자인 */
.chat-input-area {
  padding: 20px;
  display: flex;
  gap: 10px;
  background: rgba(255, 255, 255, 0.05);
  border-top: 1px solid rgba(255,255,255,0.1);
}

.chat-input-area input {
  flex: 1;
  background: rgba(255, 255, 255, 0.1);
  border: 1px solid rgba(255, 255, 255, 0.1);
  border-radius: 20px;
  padding: 12px 18px;
  color: white;
  outline: none;
}

.chat-input-area input::placeholder { color: rgba(255,255,255,0.4); }

.chat-input-area button {
  padding: 0 20px;
  background: #fff;
  color: #000;
  border: none;
  border-radius: 20px;
  font-weight: 700;
  cursor: pointer;
  transition: all 0.2s;
}

.chat-input-area button:hover:not(:disabled) {
  transform: scale(1.05);
  background: #E1603F;
  color: #fff;
}

/* 스크롤바 커스텀 */
.chat-messages::-webkit-scrollbar { width: 4px; }
.chat-messages::-webkit-scrollbar-thumb {
  background: rgba(255, 255, 255, 0.2);
  border-radius: 10px;
}
</style>