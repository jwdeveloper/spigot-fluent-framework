package io.github.jwdeveloper.spigot.fluent.core.spigot.permissions.api;

import io.github.jwdeveloper.spigot.fluent.core.common.java.StringUtils;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class PermissionModel {

    private PermissionModel parent;
    private String name = StringUtils.EMPTY;
    private String description = StringUtils.EMPTY;

    private String title = StringUtils.EMPTY;
    private List<PermissionModel> children = new ArrayList<>();

    public void addChild(PermissionModel dto)
    {
        dto.setParent(this);
        children.add(dto);
    }

    public boolean hasDescription()
    {
        return StringUtils.isNotNullOrEmpty(description);
    }

    public boolean hasParentGroup()
    {
        return parent != null;
    }

    public boolean hasChildren()
    {
        return children.size() != 0;
    }

    public boolean hasTitle()
    {
        return StringUtils.isNotNullOrEmpty(title);
    }

    public String getFullPath()
    {
        if(!hasParentGroup())
        {
            return name;
        }
        return parent.getFullPath()+"."+name;
    }

    public String getParentPath()
    {
        if(!hasParentGroup())
        {
            return StringUtils.EMPTY;
        }
        return parent.getFullPath();
    }

    public String getRealFullPath()
    {
        if(!hasChildren())
        {
            return getFullPath();
        }
        return getFullPath()+".*";
    }
}
