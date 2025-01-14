package com.example.xram.controler;

import com.example.xram.model.ChurchService;
import com.example.xram.service.ChurchServiceService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Controller
@RequestMapping("/church-services")
@RequiredArgsConstructor
public class ChurchServiceController {

    private final ChurchServiceService churchServiceService;

    @GetMapping
    public String showSchedule(
            @RequestParam(required = false) Integer year,
            @RequestParam(required = false) Integer month,
            Model model) {

        // Если год и месяц не указаны, используем текущую дату
        LocalDate today = LocalDate.now();
        year = year != null ? year : today.getYear();
        month = month != null ? month : today.getMonthValue();

        // Создаем объект YearMonth для работы с календарем
        YearMonth yearMonth = YearMonth.of(year, month);

        // Получаем первый день месяца
        LocalDate firstDay = yearMonth.atDay(1);

        // Получаем количество дней в месяце
        int daysInMonth = yearMonth.lengthOfMonth();

        // Получаем номер дня недели для первого дня (1 = Понедельник, 7 = Воскресенье)
        int firstDayOfWeek = firstDay.getDayOfWeek().getValue();

        // Создаем список для всех дней месяца
        List<CalendarDay> calendarDays = new ArrayList<>();

        // Добавляем пустые дни в начале месяца
        for (int i = 1; i < firstDayOfWeek; i++) {
            calendarDays.add(new CalendarDay());
        }

        // Добавляем все дни месяца
        for (int day = 1; day <= daysInMonth; day++) {
            LocalDate date = yearMonth.atDay(day);
            boolean hasServices = !churchServiceService.getServicesByDate(date).isEmpty();
            calendarDays.add(new CalendarDay(day, date, hasServices));
        }

        // Получаем название месяца
        String monthName = yearMonth.getMonth().getDisplayName(TextStyle.FULL, new Locale("ru"));

        // Получаем список ближайших служб
        List<ChurchService> upcomingServices = churchServiceService.getUpcomingServices();

        model.addAttribute("year", year);
        model.addAttribute("month", month);
        model.addAttribute("monthName", monthName);
        model.addAttribute("calendarDays", calendarDays);
        model.addAttribute("upcomingServices", upcomingServices);
        model.addAttribute("previousMonth", month == 1 ? 12 : month - 1);
        model.addAttribute("previousYear", month == 1 ? year - 1 : year);
        model.addAttribute("nextMonth", month == 12 ? 1 : month + 1);
        model.addAttribute("nextYear", month == 12 ? year + 1 : year);

        return "church-services/schedule";
    }
}

// Вспомогательный класс для календаря
@Data
class CalendarDay {
    private Integer day;
    private LocalDate date;
    private boolean hasServices;
    private boolean isToday;

    public CalendarDay() {
        this.day = null;
    }

    public CalendarDay(int day, LocalDate date, boolean hasServices) {
        this.day = day;
        this.date = date;
        this.hasServices = hasServices;
        this.isToday = date.equals(LocalDate.now());
    }

    public boolean isEmpty() {
        return day == null;
    }
}