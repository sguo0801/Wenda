package com.nowcoder.wenda.async.handler;

import com.nowcoder.wenda.async.EventHandler;
import com.nowcoder.wenda.async.EventModel;
import com.nowcoder.wenda.async.EventType;
import com.nowcoder.wenda.model.Message;
import com.nowcoder.wenda.model.User;
import com.nowcoder.wenda.service.MessageService;
import com.nowcoder.wenda.service.UserService;
import com.nowcoder.wenda.util.WendaUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

//点赞的异步handler
@Component
public class LikeHandler implements EventHandler {
    @Autowired
    MessageService messageService;

    @Autowired
    UserService userService;

    @Override
    public void doHandle(EventModel model) {
        Message message = new Message();
        message.setFromId(WendaUtil.SYSTEM_USERID);
        message.setToId(model.getEntityOwnerId());
        message.setCreatedDate(new Date());
        User user = userService.getUser(model.getActorId());
        message.setContent("用户" + user.getName()
                + "赞了你的评论,http://127.0.0.1:8080/question/" + model.getExt("questionId"));  //这边的questionId是要再设置一下,否则为空

        messageService.addMessage(message);
    }

    @Override
    public List<EventType> getSupportEventTypes() {  //type是枚举类型
        return Arrays.asList(EventType.LIKE);  //返回一个列表,里面的值是Like的值
    }
}
