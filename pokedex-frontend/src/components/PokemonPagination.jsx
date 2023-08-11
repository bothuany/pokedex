import React from "react";
import { AiOutlineRight, AiOutlineLeft } from "react-icons/ai";

export default function PokemonPagination({ page, setPage, totalPageNumber }) {
  const renderPageNumbers = () => {
    const pages = [];
    for (let i = 1; i <= totalPageNumber; i++) {
      pages.push(
        <a
          key={i}
          onClick={() => setPage(i)}
          className={`relative inline-flex items-center ${
            page === i
              ? "bg-gray-200 text-blue-600"
              : "text-gray-100 ring-1 ring-inset ring-gray-300 hover:bg-gray-50"
          } px-4 py-2 text-sm font-semibold ${
            page === i
              ? "focus:outline focus:outline-2"
              : "focus:z-20 focus-visible:outline-offset-2"
          }`}
        >
          {i}
        </a>
      );
    }
    return pages;
  };

  return (
    <div className="flex justify-center border-t border-gray-200 px-4 py-3 sm:px-6">
      <nav
        className="isolate inline-flex -space-x-px rounded-md shadow-sm"
        aria-label="Pagination"
      >
        {/* Previous Button */}
        <a
          className={`relative inline-flex items-center rounded-l-md px-2 py-2 text-gray-400 ring-1 ring-inset ring-gray-300 hover:bg-gray-50 ${
            page === 1 && "opacity-50 pointer-events-none"
          }`}
          onClick={() => {
            if (page > 1) setPage(page - 1);
          }}
        >
          <span className="sr-only">Previous</span>
          <AiOutlineLeft className="h-5 w-5" aria-hidden="true" />
        </a>
        {/* Page Numbers */}
        {renderPageNumbers()}
        {/* Next Button */}
        <a
          className={`relative inline-flex items-center rounded-r-md px-2 py-2 text-gray-400 ring-1 ring-inset ring-gray-300 hover:bg-gray-50 ${
            page === totalPageNumber && "opacity-50 pointer-events-none"
          }`}
          onClick={() => {
            if (page < totalPageNumber) setPage(page + 1);
          }}
        >
          <span className="sr-only">Next</span>
          <AiOutlineRight className="h-5 w-5" aria-hidden="true" />
        </a>
      </nav>
    </div>
  );
}
