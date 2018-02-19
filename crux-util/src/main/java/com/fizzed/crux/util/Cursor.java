package com.fizzed.crux.util;

import java.util.Objects;
import java.util.Optional;

public class Cursor {
 
    private final Long offset;
    private final Long limit;
    private final Long before;
    private final Long after;
    
    public Cursor(Long offset, Long limit) {
        this(offset, limit, null, null);
    }

    public Cursor(Long offset, Long limit, Long before, Long after) {
        this.offset = offset;
        this.limit = limit;
        this.before = before;
        this.after = after;
    }

    public Long getOffset() {
        return offset;
    }

    public Cursor setOffset(Long offset) {
        return new Cursor(offset, this.limit, this.before, this.after);
    }

    public Long getLimit() {
        return limit;
    }

    public Cursor setLimit(Long limit) {
        return new Cursor(this.offset, limit, this.before, this.after);
    }
    
    public Cursor nextByOffsetLimit() {
        return new Cursor(this.offset+limit, this.limit, this.before, this.after);
    }
    
    public Long getBefore() {
        return before;
    }

    public Cursor setBefore(Long before) {
        return new Cursor(this.offset, this.limit, before, this.after);
    }

    public Long getAfter() {
        return after;
    }

    public Cursor setAfter(Long after) {
        return new Cursor(this.offset, this.limit, this.before, after);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        if (this.offset != null) {
            if (sb.length() > 0) {sb.append(","); }
            sb.append("o:").append(this.offset);
        }
        if (this.limit != null) {
            if (sb.length() > 0) {sb.append(","); }
            sb.append("l:").append(this.limit);
        }
        if (this.before != null) {
            if (sb.length() > 0) {sb.append(","); }
            sb.append("b:").append(this.before);
        }
        if (this.after != null) {
            if (sb.length() > 0) {sb.append(","); }
            sb.append("a:").append(this.after);
        }
        return sb.toString();
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + Objects.hashCode(this.offset);
        hash = 97 * hash + Objects.hashCode(this.limit);
        hash = 97 * hash + Objects.hashCode(this.before);
        hash = 97 * hash + Objects.hashCode(this.after);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Cursor other = (Cursor) obj;
        if (!Objects.equals(this.offset, other.offset)) {
            return false;
        }
        if (!Objects.equals(this.limit, other.limit)) {
            return false;
        }
        if (!Objects.equals(this.before, other.before)) {
            return false;
        }
        if (!Objects.equals(this.after, other.after)) {
            return false;
        }
        return true;
    }

    static public Optional<Cursor> parse(String s) {
        if (s == null) {
            return Optional.empty();
        }
        s = s.trim();
        if (s.isEmpty()) {
            return Optional.empty();
        }
        Long offset = null;
        Long limit = null;
        Long after = null;
        Long before = null;
        String[] tokens = s.split(",");
        for (String token : tokens) {
            String[] nvs = token.split(":");
            if (nvs.length != 2) {
                throw new IllegalArgumentException("Invalid token " + token);
            }
            String tag = nvs[0];
            Long val = Long.valueOf(nvs[1]);
            switch (tag) {
                case "o":
                    offset = val;
                    break;
                case "l":
                    limit = val;
                    break;
                case "a":
                    after = val;
                    break;
                case "b":
                    before = val;
                    break;
                default:
                    throw new IllegalArgumentException("Unsupported tag " + tag);
            }
        }
        return Optional.of(new Cursor(offset, limit, before, after));
    }

}