package ru.job4j.pooh;

import java.util.concurrent.*;

import static ru.job4j.pooh.Req.GET;
import static ru.job4j.pooh.Req.POST;

public class QueueService implements Service {
    private final ConcurrentHashMap<String, ConcurrentLinkedQueue<String>> queue = new ConcurrentHashMap<>();

    @Override
    public Resp process(Req req) {
        String text = "";
        String status = "200";
        if (POST.equals(req.httpRequestType())) {
            queue.putIfAbsent(req.getSourceName(), new ConcurrentLinkedQueue<>());
            queue.get(req.getSourceName()).offer(req.getParam());
            status = "201";
        } else if (GET.equals(req.httpRequestType())) {

            text = queue.get(req.getSourceName()).poll();
            if (text == null) {
                text = "";
                status = "204";
            }
        }
        return new Resp(text, status);
    }
}
