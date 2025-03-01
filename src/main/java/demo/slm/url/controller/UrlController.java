package demo.slm.url.controller;

import demo.slm.url.controller.port.UrlService;
import demo.slm.url.controller.response.ResponseDto;
import demo.slm.url.domain.ShortUrlCreate;
import java.time.LocalDate;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class UrlController {

    private final UrlService urlService;

    public UrlController(UrlService urlService) {
        this.urlService = urlService;
    }

    @ResponseBody
    @PostMapping("/s")
    public ResponseEntity<ResponseDto> createShortLink(@RequestBody ShortUrlCreate shortUrlCreate) {
        return ResponseEntity.ok(ResponseDto.of(urlService.createShortLink(shortUrlCreate)));
    }

    @ResponseBody
    @GetMapping("/g/{shortUrlId}")
    public ResponseEntity<ResponseDto> searchShortLink(@PathVariable String shortUrlId) {
        return ResponseEntity.ok(ResponseDto.of(urlService.searchShortLink(shortUrlId)));
    }

    @GetMapping("/r/{shortUrlId}")
    public String redirectOriginalLink(@PathVariable String shortUrlId) {
        return "redirect:" + urlService.getOriginalLink(shortUrlId);
    }

    @ResponseBody
    @GetMapping("/st/{shortUrlId}")
    public ResponseEntity<ResponseDto> searchDailyClickCount(@PathVariable String shortUrlId,
                                                             @RequestParam LocalDate date) {
        return ResponseEntity.ok(ResponseDto.of(urlService.searchDailyClickCount(shortUrlId, date)));
    }

}
