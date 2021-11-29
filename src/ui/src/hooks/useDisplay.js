import {useEffect, useState} from "react";

export const useMobile = () => {
    return useBreakpoint(768);
}

export const useMediumDisplay = () => {
    return useBreakpoint(992);
}

export const useBreakpoint = (pixel) => {
    const [isBreakpoint, setIsBreakpoint] = useState(true);
    const handleResize = (px) => setIsBreakpoint(window.innerWidth < px)
    useEffect(() => {
        window.addEventListener("resize", handleResize.bind(this, pixel));
        handleResize(pixel);
        return () => {
            window.removeEventListener("resize", handleResize.bind(this, pixel));
        };
    }, [pixel]);
    return isBreakpoint;
}