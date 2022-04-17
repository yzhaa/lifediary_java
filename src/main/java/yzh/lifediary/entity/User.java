package yzh.lifediary.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.experimental.Accessors;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * <p>
 * 
 * </p>
 *
 * @author yzh
 * @since 2022-03-13
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class User implements Serializable , UserDetails {


    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @JSONField(serialize = false)
    private Integer account;


    private String name;



    @TableField(value = "icon_path")
    private String iconPath;

    @JSONField(serialize = false)
    private String password;

    @JSONField(serialize = false)
    @Getter(value = AccessLevel.NONE)
    private Boolean enabled;

    @JSONField(serialize = false)
    private Boolean locked;

    @TableField(exist = false)
    @JSONField(serialize = false)
    public List<SimpleGrantedAuthority> list = new LinkedList<>();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return list;
    }

    //secure 框架的
    @Override
    public String getUsername() {

        return  account.toString();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {

        return !locked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    public void setAuthorities(List<Role> roles) {
        for (Role r:roles) {
            list.add(new SimpleGrantedAuthority(r.getName()));
        }
    }
}
