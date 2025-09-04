package com.IIE.Industrial_Innovation_Engine_server.entity;

public class GameTagRelation {
    private Long id;
    private Long gameId;
    private Long tagId;

    // 无参构造函数
    public GameTagRelation() {
    }

    // 带参构造函数
    public GameTagRelation(Long gameId, Long tagId) {
        this.gameId = gameId;
        this.tagId = tagId;
    }

    // Getter 和 Setter 方法
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getGameId() {
        return gameId;
    }

    public void setGameId(Long gameId) {
        this.gameId = gameId;
    }

    public Long getTagId() {
        return tagId;
    }

    public void setTagId(Long tagId) {
        this.tagId = tagId;
    }

    // toString 方法
    @Override
    public String toString() {
        return "GameTagRelation{" +
                "id=" + id +
                ", gameId=" + gameId +
                ", tagId=" + tagId +
                '}';
    }

    // equals 和 hashCode 方法
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        GameTagRelation that = (GameTagRelation) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (gameId != null ? !gameId.equals(that.gameId) : that.gameId != null) return false;
        return tagId != null ? tagId.equals(that.tagId) : that.tagId == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (gameId != null ? gameId.hashCode() : 0);
        result = 31 * result + (tagId != null ? tagId.hashCode() : 0);
        return result;
    }
}
