package zzd.com.domain;

import java.util.Arrays;

/**
 * 定义用户角色
 */
public class Role {
        private Integer id;
        private String username;
        private Integer[] Authority;
        private String describe;

        public Integer getId(int x) { return id; }

        public void setId(Integer id) { this.id = id; }

        public String getUsername() { return username; }

        public void setUsername(String username) { this.username = username; }

        public Integer[] getAuthority() { return Authority; }

        public void setAuthority(Integer[] authority) { Authority = authority; }

        public String getDescribe() { return describe; }

        public void setDescribe(String describe) { this.describe = describe; }

        @Override
        public String toString() {
                return "Role{" +
                        "id=" + id +
                        ", username='" + username + '\'' +
                        ", Authority=" + Arrays.toString(Authority) +
                        ", describe='" + describe + '\'' +
                        '}';
        }
}
