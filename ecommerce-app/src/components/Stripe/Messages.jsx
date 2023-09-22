import React from "react";

const Messages = ({ messages }) => {
  return (
    <div className="bg-indigo-600 px-4 py-2 flex flex-col gap-3">
      {messages?.map?.((msg, index) => (
        <p className="text-white" key={index}>
          {msg}
        </p>
      ))}
    </div>
  );
};

export default Messages;
