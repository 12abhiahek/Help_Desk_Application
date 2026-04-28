package com.helpdesk.HelpDeskWebApplication.service;

import com.helpdesk.HelpDeskWebApplication.tools.EmailTool;
import com.helpdesk.HelpDeskWebApplication.tools.TicketDatabaseTool;
import jakarta.annotation.Resource;
import lombok.Value;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
public class AiService {

    private final ChatClient chatClient;

    private final TicketDatabaseTool ticketDatabaseTool;
    private final EmailTool emailTool;

    //@Value(staticConstructor = "classpath:/helpdesk-system.st")
    protected Resource systemPromptResource;

    public AiService(ChatClient chatClient, TicketDatabaseTool ticketDatabaseTool, EmailTool emailTool) {
        this.chatClient = chatClient;
        this.ticketDatabaseTool = ticketDatabaseTool;
        this.emailTool = emailTool;
    }

    public String getResponseFromAssistant(String query, String conversationId) {

        //basic call to llm
        return this.chatClient
                .prompt()
                .advisors(advisorSpec -> advisorSpec.param(ChatMemory.CONVERSATION_ID, conversationId))
                //tool informations
                .tools(ticketDatabaseTool, emailTool)
                .system(String.valueOf(systemPromptResource))
                .user(query)
                .call()
                .content();


    }

    public Flux<String> streamResponseFromAssistant(String query, String conversationId) {


        return this.chatClient
                .prompt()
                .advisors(advisorSpec -> advisorSpec.param(ChatMemory.CONVERSATION_ID, conversationId))
                //tool informations
                .tools(ticketDatabaseTool, emailTool)
                .system(String.valueOf(systemPromptResource))
                .user(query)
                .stream().content();

    }


}
