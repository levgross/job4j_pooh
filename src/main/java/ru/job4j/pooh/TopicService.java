package ru.job4j.pooh;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import static ru.job4j.pooh.Req.GET;
import static ru.job4j.pooh.Req.POST;

public class TopicService implements Service {
    private final ConcurrentHashMap<String, ConcurrentHashMap<String, ConcurrentLinkedQueue<String>>> topic
            = new ConcurrentHashMap<>();
    @Override
    public Resp process(Req req) {
        String text = "";
        String status = "200";
        if (POST.equals(req.httpRequestType())) {
            for (ConcurrentLinkedQueue<String> queue : topic.get(req.getSourceName()).values()) {
                queue.offer(req.getParam());
            }
            status = "201";
        } else if (GET.equals(req.httpRequestType())) {
            topic.putIfAbsent(req.getSourceName(), new ConcurrentHashMap<>());
            topic.get(req.getSourceName()).putIfAbsent(req.getParam(), new ConcurrentLinkedQueue<>());
            text = topic.get(req.getSourceName()).get(req.getParam()).poll();
            if (text == null) {
                text = "";
                status = "204";
            }
        }
        return new Resp(text, status);
    }
}
