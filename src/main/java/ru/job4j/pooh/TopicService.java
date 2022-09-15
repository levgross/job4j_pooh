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
            for (ConcurrentHashMap<String, ConcurrentLinkedQueue<String>> map : topic.values()) {
                map.get(req.getSourceName()).offer(req.getParam());
            }
            status = "201";
        } else if (GET.equals(req.httpRequestType())) {
            topic.putIfAbsent(req.getParam(), new ConcurrentHashMap<>());
            topic.get(req.getParam()).putIfAbsent(req.getSourceName(), new ConcurrentLinkedQueue<>());
            text = topic.get(req.getParam()).get(req.getSourceName()).poll();
            if (text == null) {
                text = "";
                status = "204";
            }
        }
        return new Resp(text, status);
    }
}
