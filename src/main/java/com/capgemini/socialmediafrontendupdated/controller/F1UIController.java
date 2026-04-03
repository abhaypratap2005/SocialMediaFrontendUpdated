package com.capgemini.socialmediafrontendupdated.controller;

import com.capgemini.socialmediafrontendupdated.dto.*;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Page;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import com.capgemini.socialmediafrontendupdated.dto.TrendingDTO;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Controller
public class F1UIController {

    @Autowired
    private RestTemplate restTemplate;

    private final String BASE_URL = "http://localhost:8082/api";

    // 🔥 HELPER METHOD (CORE)
    private <T> ResponseEntity<T> callApiWithJwt(
            String url,
            HttpMethod method,
            HttpSession session,
            Class<T> responseType) {

        String token = (String) session.getAttribute("JWT_TOKEN");

        if (token == null) {
            throw new RuntimeException("User not logged in");
        }

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + token);

        HttpEntity<String> entity = new HttpEntity<>(headers);

        return restTemplate.exchange(url, method, entity, responseType);
    }

    // 🏎️ FEED
    @GetMapping("/feed/{userId}")
    public String showFeed(@PathVariable int userId,
                           HttpSession session,
                           Model model) {

        String url = BASE_URL + "/users/" + userId + "/feed";

        try {
            ResponseEntity<FeedDTO[]> response =
                    callApiWithJwt(url, HttpMethod.GET, session, FeedDTO[].class);

            model.addAttribute("posts", Arrays.asList(response.getBody()));
            model.addAttribute("userId", userId);

            return "feed";

        } catch (Exception e) {
            session.invalidate();
            return "redirect:/login";
        }
    }

    // 👥 FRIENDS
    @GetMapping("/friends/{userId}")
    public String showFriends(@PathVariable int userId,
                              HttpSession session,
                              Model model) {

        String url = BASE_URL + "/users/" + userId + "/friends";

        try {
            ResponseEntity<FriendDTO[]> response =
                    callApiWithJwt(url, HttpMethod.GET, session, FriendDTO[].class);

            model.addAttribute("friends", Arrays.asList(response.getBody()));
            model.addAttribute("userId", userId);

            return "friends";

        } catch (Exception e) {
            session.invalidate();
            return "redirect:/login";
        }
    }

    // 🔔 NOTIFICATIONS
    @GetMapping("/notifications/{userId}")
    public String showNotifications(@PathVariable int userId,
                                    HttpSession session,
                                    Model model) {

        String url = BASE_URL + "/users/" + userId + "/notifications";

        try {
            ResponseEntity<NotificationDTO[]> response =
                    callApiWithJwt(url, HttpMethod.GET, session, NotificationDTO[].class);

            model.addAttribute("notifications", Arrays.asList(response.getBody()));
            model.addAttribute("userId", userId);

            return "notifications";

        } catch (Exception e) {
            session.invalidate();
            return "redirect:/login";
        }
    }

    // 💬 MESSAGES
    @GetMapping("/messages/{userId}")
    public String showMessages(@PathVariable int userId,
                               HttpSession session,
                               Model model) {

        String url = BASE_URL + "/users/" + userId + "/messages";

        try {
            ResponseEntity<MessageDTO[]> response =
                    callApiWithJwt(url, HttpMethod.GET, session, MessageDTO[].class);

            model.addAttribute("messages", Arrays.asList(response.getBody()));
            model.addAttribute("userId", userId);

            return "messages";

        } catch (Exception e) {
            session.invalidate();
            return "redirect:/login";
        }
    }

    // 📄 POST DETAILS
    @GetMapping("/post/{postId}/{userId}")
    public String showPostDetails(@PathVariable int postId,
                                  @PathVariable int userId,
                                  HttpSession session,
                                  Model model) {

        try {
            String base = BASE_URL + "/users/" + userId + "/posts/" + postId;

            // 🔥 GET POST (main data only)
            ResponseEntity<PostDTO> postResponse =
                    callApiWithJwt(base, HttpMethod.GET, session, PostDTO.class);

            PostDTO post = postResponse.getBody();

            if (post == null) {
                return "redirect:/feed/" + userId;
            }

            // 🔥 SET MODEL (ONLY POST)
            model.addAttribute("post", post);

            // ✅ COUNTS FROM BACKEND
            model.addAttribute("likesCount",
                    post.getLikeCount() != null ? post.getLikeCount() : 0);

            model.addAttribute("commentsCount",
                    post.getCommentCount() != null ? post.getCommentCount() : 0);

            model.addAttribute("postId", postId);
            model.addAttribute("userId", userId);

            return "post";

        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/feed/" + userId;
        }
    }

    // ❤️ LIKES
    @GetMapping("/post/{postId}/{userId}/likes")
    public String showPostLikes(@PathVariable int postId,
                                @PathVariable int userId,
                                HttpSession session,
                                Model model) {

        String url = BASE_URL + "/users/" + userId + "/posts/" + postId + "/likes";

        try {
            ResponseEntity<LikeDTO[]> response =
                    callApiWithJwt(url, HttpMethod.GET, session, LikeDTO[].class);

            model.addAttribute("likes", Arrays.asList(response.getBody()));
            model.addAttribute("postId", postId);
            model.addAttribute("userId", userId);

            return "post-likes";

        } catch (Exception e) {
            session.invalidate();
            return "redirect:/login";
        }
    }

    // 💬 COMMENTS
    @GetMapping("/post/{postId}/{userId}/comments")
    public String showPostComments(@PathVariable int postId,
                                   @PathVariable int userId,
                                   HttpSession session,
                                   Model model) {

        String url = BASE_URL + "/users/" + userId + "/posts/" + postId + "/comments";

        try {
            ResponseEntity<CommentDTO[]> response =
                    callApiWithJwt(url, HttpMethod.GET, session, CommentDTO[].class);

            model.addAttribute("comments", Arrays.asList(response.getBody()));
            model.addAttribute("postId", postId);
            model.addAttribute("userId", userId);

            return "post-comments";

        } catch (Exception e) {
            session.invalidate();
            return "redirect:/login";
        }
    }

    // 🔐 LOGIN PAGE
    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    // 🔐 ROOT REDIRECT
    @GetMapping("/")
    public String rootRedirect() {
        return "redirect:/login";
    }

    // 🔐 LOGIN
    @PostMapping("/login")
    public String login(@RequestParam String username,
                        @RequestParam String password,
                        HttpSession session,
                        Model model) {

        String url = BASE_URL + "/auth/login";

        try {
            AuthRequest request = new AuthRequest();
            request.setUsername(username);
            request.setPassword(password);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<AuthRequest> entity =
                    new HttpEntity<>(request, headers);

            // 🔥 CHANGE HERE
            ResponseEntity<Map> response =
                    restTemplate.postForEntity(url, entity, Map.class);

            Map body = response.getBody();

            String token = (String) body.get("token");
            Integer userId = (Integer) body.get("userId");

            session.setAttribute("JWT_TOKEN", token);
            session.setAttribute("USER_ID", userId);

            return "redirect:/feed/" + userId;

        } catch (Exception e) {
            model.addAttribute("error", "Invalid username or password");
            return "login";
        }
    }

    // 🚪 LOGOUT
    @GetMapping("/web/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }


    @GetMapping("/trending")
    public String trendingPage(HttpSession session, Model model) { // Add HttpSession here

        List<String> topics = List.of("tech", "adventure", "coding", "travel");
        List<TrendingDTO> allPosts = new ArrayList<>();
        Integer userId = (Integer) session.getAttribute("USER_ID");
        try {
            for (String topic : topics) {
                String url = "http://localhost:8082/api/trending/" + topic;

                // Use your helper method to keep logic consistent and include JWT
                ResponseEntity<TrendingWrapperDTO> response =
                        callApiWithJwt(url, HttpMethod.GET, session, TrendingWrapperDTO.class);

                if (response.getBody() != null && response.getBody().getContent() != null) {
                    allPosts.addAll(response.getBody().getContent());
                }
            }
            model.addAttribute("trendingData", allPosts);
            model.addAttribute("userId", userId);
            return "trending";

        } catch (Exception e) {
            // If JWT is missing or expired, redirect to login
            return "redirect:/login";
        }
    }
}
