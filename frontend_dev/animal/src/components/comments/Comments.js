import React, { useState, useEffect } from "react";
import CommentItem from "./CommentItem";

function Comments({ comments, getComments }) {
  return (
    <div>
      {comments.map((item, idx) => {
        return (
          <CommentItem
            isAuthor={true}
            item={item}
            key={idx}
            getComments={getComments}
          />
        );
      })}
    </div>
  );
}

export default Comments;
