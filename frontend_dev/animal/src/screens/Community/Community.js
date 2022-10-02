import React, { useState, useEffect } from "react";
import "./Community.css";
import LostDetailList from "./LostDetailList";
import { Link, useLocation } from "react-router-dom";
import CommunityShowpet from "./CommunityShowpet";

function Community() {
  const [comType, setComType] = useState("showpet");

  const location = useLocation();
  useEffect(() => {
    console.log(location);
    if (location.pathname === "/lost/list") {
      setComType("lost");
    }
  }, []);

  return (
    <div id="community">
      <div className="background__banner">
        <img
          className="banner-img"
          src=".././CombannerImg.jpg"
          alt="combannerimg"
        />
      </div>
      <div className="item-content">
        <div className="banners">
          <Link to={`/show-pet/list`}>
            <button
              type="button"
              onClick={() => {
                setComType("showpet");
              }}
              className={`banner-item ${comType === "showpet" ? "active" : ""}`}
            >
              자랑하기
            </button>
          </Link>
          <Link to={`/lost/list`}>
            <button
              onClick={() => {
                setComType("lost");
              }}
              className={`banner-item ${comType === "lost" ? "active" : ""}`}
              type="button"
            >
              실종동물찾기
            </button>
          </Link>
        </div>
        {comType === "showpet" ? <CommunityShowpet /> : <LostDetailList />}
      </div>
    </div>
  );
}
export default Community;
