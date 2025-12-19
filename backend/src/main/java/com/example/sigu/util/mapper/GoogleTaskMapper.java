package com.example.sigu.util.mapper;

import com.example.sigu.persistence.entity.Tarea;
import com.google.api.client.util.DateTime;
import com.google.api.services.tasks.model.Task;
import org.springframework.stereotype.Service;

import java.time.ZoneId;
import java.time.ZonedDateTime;

@Service
public class GoogleTaskMapper {

    public Task toTask(Tarea tarea) {
        Task task = new Task();

        if (tarea.getTitulo() != null) task.setTitle(tarea.getTitulo());

        if (tarea.getDescripcion() != null) {
            String notes = String.format("Prioridad %s\n%s", tarea.getPrioridad(), tarea.getDescripcion());
            task.setNotes(notes);
        }

        if (tarea.getFechaEntrega() != null) {
            ZonedDateTime zdt = tarea.getFechaEntrega().atTime(23, 59).atZone(ZoneId.systemDefault());
            task.setDue(new DateTime(zdt.toInstant().toEpochMilli()).toStringRfc3339());
        }

        if (tarea.getEstado() != null) task.setStatus(tarea.getEstado().getGoogleStatus());

        return task;
    }
}
