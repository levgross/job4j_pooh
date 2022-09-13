package ru.job4j.pooh;

import java.util.concurrent.*;

import static ru.job4j.pooh.Req.GET;
import static ru.job4j.pooh.Req.POST;

public class QueueService implements Service {
    private final ConcurrentHashMap<String, ConcurrentLinkedQueue<String>> queue = new ConcurrentHashMap<>();

    @Override
    public Resp process(Req req) {
        if (POST.equals(req.httpRequestType())) {
            queue.putIfAbsent(req.getSourceName(), new ConcurrentLinkedQueue<>());
            queue.get(req.getSourceName()).offer(req.getParam());
            return null;
        }
        String text = queue.get(req.getSourceName()).poll();
        String status = "200";
        if (GET.equals(req.httpRequestType())) {
            if (text == null) {
                status = "204";
            }
        }
        return new Resp(text, status);
    }
}
