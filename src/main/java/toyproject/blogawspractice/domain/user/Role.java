package toyproject.blogawspractice.domain.user;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Role {
    // Role.name() == GUEST, USER, ADMIN 으로 출력
    GUEST("ROLE_GUEST", "게스트 유저"),
    USER("ROLE_USER", "일반 사용자"),
    ADMIN("ROLE_ADMIN", "관리자");

    private final String key;
    private final String title;
}
